package com.woojun.fried_chat.public_community

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.woojun.fried_chat.R
import com.woojun.fried_chat.account.User
import com.woojun.fried_chat.databinding.ActivityPublicWriteBinding

class PublicWriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPublicWriteBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var url = ""

    private val getContent: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                Glide.with(this)
                    .load(it)
                    .centerCrop()
                    .into(binding.imageView)
                binding.imageText.visibility = View.GONE
                uploadImageAndGetUrl(
                    it,
                    onSuccess = { imageUrl ->
                        url = imageUrl
                    },
                    onFailure = { _ ->
                        Toast.makeText(this, "에러", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.apply {

            database.child("users").child(auth.uid.toString()).get().addOnSuccessListener {
                val user = it.getValue(User::class.java)!!

                finishButton.setOnClickListener {
                    if (url != "" && input.text.isNotEmpty()) {
                        database.child("public").child(System.currentTimeMillis().toString()).setValue(PublicFeed(
                            user.nickname, user.image, url, input.text.toString(), 0, 0, true
                        ))
                        finish()
                    } else {
                        Toast.makeText(this@PublicWriteActivity, "사진과 글 모두 넣어주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener{
                Toast.makeText(this@PublicWriteActivity, "에러", Toast.LENGTH_SHORT).show()
            }

            imageView.setOnClickListener {
                getContent.launch("image/*")
            }


        }
    }

    private fun uploadImageAndGetUrl(imageUri: Uri, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val storageRef = Firebase.storage.reference
        val imageFileName = "${System.currentTimeMillis()}_${imageUri.lastPathSegment}"
        val imageRef = storageRef.child("images/$imageFileName")

        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener { _ ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onSuccess(uri.toString())
            }.addOnFailureListener { exception ->
                onFailure(exception)
            }
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }
}