package fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myvault.R
import com.example.myvault.databinding.FragmentPDBinding
import com.example.myvault.rpdfview
import com.example.myvault.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask.TaskSnapshot
import com.google.firebase.storage.ktx.storage

class PDF : Fragment() {
    private lateinit var binding: FragmentPDBinding
    private var pdfreference = Firebase.storage.reference
    private var currentfile: Uri? = null
    private lateinit var database1: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPDBinding.inflate(layoutInflater)
        database1 = FirebaseDatabase.getInstance().getReference("User ID")
        firebaseAuth = FirebaseAuth.getInstance()


        binding.button5.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "application/pdf"
                pdfLauncher.launch(it)
            }
        }
        binding.textview4.setOnClickListener {
            val intent = Intent(binding.root.context,rpdfview::class.java)
            startActivity(intent)
        }

        binding.button6.setOnClickListener {
            val filename1 = binding.edittextview1.text.toString()
            if (filename1.isNotEmpty()) {
                uploadPDFStorage(filename = filename1)
            } else {
                Toast.makeText(binding.root.context, "Enter file name", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }


    private val pdfLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            binding.pdfview.setImageResource(R.drawable.pdf_uploaded)
            if (result.resultCode == Activity.RESULT_OK) {
                result?.data?.data?.let {

                    currentfile = it
                }
            } else {
                Toast.makeText(binding.root.context, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun uploadPDFStorage(filename: String) {
        try {
            currentfile?.let {
                val email = firebaseAuth.currentUser?.email
                val file_name = user(filename, ".pdf", it.toString())

                pdfreference.child("pdf/$filename").putFile(it).addOnSuccessListener {
                    pdfreference.child("pdf/$filename").downloadUrl.addOnSuccessListener { uri ->
                        val file_name = user(filename, ".pdf", uri.toString())
                        if (email != null) {
                            database1.child(email.removeSuffix("@gmail.com")).child("PDF").child(filename)
                                .setValue(file_name)
                                }
                    }
                    binding.pdfview.setImageResource(R.drawable.pdf)
                    binding.edittextview1.setText("")
                    Toast.makeText(
                        binding.root.context,
                        "successfully uploaded",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnFailureListener {
                    Toast.makeText(binding.root.context, it.toString(), Toast.LENGTH_SHORT).show()

                }.addOnProgressListener { TaskSnapshot->
                    val process: Double = (100.0*(TaskSnapshot.bytesTransferred))/TaskSnapshot.totalByteCount
                    binding.progressbar.progress= process.toInt()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(binding.root.context, e.toString(), Toast.LENGTH_SHORT).show()

        }
    }


}