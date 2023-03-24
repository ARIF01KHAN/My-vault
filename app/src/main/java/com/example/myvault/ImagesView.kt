package com.example.myvault

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvault.databinding.ActivityImagesViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ImagesView : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imagelist : ArrayList<Files>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseReference1: DatabaseReference
    private lateinit var databaseReference2: DatabaseReference
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_view)
        recyclerView= findViewById(R.id.Rimageview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        firebaseAuth = FirebaseAuth.getInstance()
        val email = firebaseAuth.currentUser?.email

        imagelist = arrayListOf<Files>()



        databaseReference = FirebaseDatabase.getInstance().getReference("User ID")
        databaseReference1 = databaseReference.child(email!!.removeSuffix("@gmail.com"))
        databaseReference2 = databaseReference1.child("Image")
        databaseReference2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(dataSnapshot in snapshot.children){
                        val user= dataSnapshot.getValue(Files::class.java)
                        imagelist.add(user!!)
                }
                    recyclerView.adapter = ImageAdapter(imagelist , this@ImagesView)
            }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ImagesView , error.toString(),Toast.LENGTH_SHORT).show()
            }
        })

    }
}

