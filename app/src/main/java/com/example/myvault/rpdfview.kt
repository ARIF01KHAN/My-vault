package com.example.myvault

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.EnumSet.range

class rpdfview : AppCompatActivity() {
    private lateinit var listView1: ListView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseReference1: DatabaseReference
    private lateinit var databaseReference2: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var filelist : ArrayList<Files>
    private lateinit var list1 : Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rpdfview)
        listView1 = findViewById(R.id.listview)
        firebaseAuth = FirebaseAuth.getInstance()
        var email = firebaseAuth.currentUser!!.email

        filelist = arrayListOf<Files>()
        list1 = arrayOf()

        databaseReference = FirebaseDatabase.getInstance().getReference("User ID")
        databaseReference1 = databaseReference.child(email!!.removeSuffix("@gmail.com"))
        databaseReference2 = databaseReference1.child("PDF")
        databaseReference2.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (dataSnapshot in snapshot.children){
                        val user= dataSnapshot.getValue(Files::class.java)
                        filelist.add(user!!)
                    }
                    for(i in filelist){
                        list1 = list1.plusElement(i.filename.toString())
                    }
                var adapter =ArrayAdapter(this@rpdfview,android.R.layout.simple_list_item_1,list1)
                    listView1.adapter = adapter
                    listView1.onItemClickListener = object : AdapterView.OnItemClickListener {
                        override fun onItemClick(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long,
                        ) {
                            var intent = Intent(this@rpdfview, openpdf::class.java)
                            intent.putExtra("Url", filelist[p2].url.toString())
                            startActivity(intent)
                        }

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@rpdfview, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })



    }
}