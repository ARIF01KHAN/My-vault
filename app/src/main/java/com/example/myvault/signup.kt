package com.example.myvault

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myvault.databinding.RegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {
    private lateinit var binding: RegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database1: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database1= FirebaseDatabase.getInstance().getReference("User Id")

        firebaseAuth = FirebaseAuth.getInstance()
        binding.button2.setOnClickListener {
            val intent = Intent(this, signin::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val pass = binding.editTextTextPassword.text.toString()

            if (firebaseAuth.currentUser != null){
                if (email.isNotEmpty() && pass.isNotEmpty()) {
                    val user1= user(email)
                    database1.child(email.removeSuffix("@gmail.com"))
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, signin::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                else{
                    Toast.makeText(this, "Empty Field are not Allowed", Toast.LENGTH_SHORT).show()
                }
            run{
                    Toast.makeText(this, "User already registered", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
