package fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myvault.ImagesView
import com.example.myvault.R
import com.example.myvault.databinding.FragmentImagesBinding
import com.example.myvault.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@Suppress("UNREACHABLE_CODE")
class Images : Fragment() {
    private lateinit var binding: FragmentImagesBinding
    private var imagereference = Firebase.storage.reference
    private var currentfile: Uri? = null
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database=FirebaseDatabase.getInstance().getReference("User ID")

        binding = FragmentImagesBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        val email= firebaseAuth.currentUser?.getEmail()


        binding.button3.setOnClickListener{
            Intent(Intent.ACTION_GET_CONTENT).also{
                it.type = "image/*"
                imageLauncher.launch(it)
                }
            }
        binding.textview3.setOnClickListener{
            val intent = Intent(binding.root.context,ImagesView::class.java)
            startActivity(intent)

        }
        binding.button4.setOnClickListener{
            val filename1 = binding.edittextview.text.toString()
            if (filename1.isNotEmpty()){
                uploadImageToStorage(filename = filename1)
            }
            else {
                Toast.makeText(binding.root.context,"Enter Filename",Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
        }




    private val imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == RESULT_OK){
            result?.data?.data?.let{
                currentfile = it
                binding.imageview3.setImageURI(it)
            }
        } else{
            Toast.makeText(binding.root.context,"Cancelled",Toast.LENGTH_SHORT).show()
        }
    }
    private fun uploadImageToStorage(filename: String) {
        try {
            currentfile?.let{
                val email= firebaseAuth.currentUser?.getEmail()
                val file_name=user(filename,".jpeg",it.toString())

                imagereference.child("images/$filename").putFile(it).addOnSuccessListener {
                    imagereference.child("images/$filename").downloadUrl.addOnSuccessListener {uri ->
                        val file_name=user(filename,".jpeg",uri.toString())
                if (email != null) {
                    database.child(email.removeSuffix("@gmail.com")).child("Image").child(filename).setValue(file_name).addOnSuccessListener {

                    }
                }

                    }
                    binding.edittextview.setText("")
                    binding.imageview3.setImageResource(R.drawable.image)
                    Toast.makeText(binding.root.context, "successfully uploaded",Toast.LENGTH_SHORT).show()
                } .addOnFailureListener {
                    Toast.makeText(binding.root.context,it.toString(),Toast.LENGTH_SHORT).show()

                }.addOnProgressListener { taskSnapshot ->
                    val progress:Double =(100.0*(taskSnapshot.bytesTransferred))/taskSnapshot.totalByteCount
                    binding.progressbar.progress=progress.toInt()
                }
            }
        } catch (e: Exception){
            Toast.makeText(binding.root.context, e.toString(),Toast.LENGTH_SHORT).show()

        }
    }

}






