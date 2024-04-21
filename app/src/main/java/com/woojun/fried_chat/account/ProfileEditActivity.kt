package com.woojun.fried_chat.account

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.woojun.fried_chat.MainActivity
import com.woojun.fried_chat.R
import com.woojun.fried_chat.databinding.ActivityProfileEditBinding
import com.woojun.fried_chat.private_community.Gender

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var gender = ""
    private val genderList = arrayOf(
        "Gay",
        "Lesbian",
        "Transgender",
    )

    private var url = ""

    private val getContent: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                Glide.with(this)
                    .load(it)
                    .centerCrop()
                    .into(binding.imageView3)
                binding.imageView3.visibility = View.GONE
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
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.apply {

            database.child("users").child(auth.uid.toString()).get().addOnSuccessListener {
                val user = it.getValue(User::class.java)!!

                imageView3.setOnClickListener {
                    getContent.launch("image/*")
                }

                binding.genderBox.setOnClickListener {
                    val dialog = AlertDialog.Builder(this@ProfileEditActivity)
                    dialog.setTitle("Gender")
                        .setItems(genderList) { _, index ->
                            gender = when (index) {
                                0 -> {
                                    genderText.text = "Gay"
                                    "g"
                                }
                                1 -> {
                                    genderText.text = "Lesbian"
                                    "l"
                                }
                                2 -> {
                                    genderText.text = "Transgender"
                                    "t"
                                }
                                else -> {
                                    genderText.text = "Gay"
                                    "g"
                                }
                            }
                            genderText.setTextColor(Color.BLACK)
                        }
                    dialog.show()
                }

                binding.finishButton.setOnClickListener {
                    if (nicknameInput.text.isNotEmpty() && gender != "") {

                        val genderValue = when(gender) {
                            "g" -> {
                                Gender.Gay
                            }
                            "l" -> {
                                Gender.Lesbian
                            }
                            "t" -> {
                                Gender.Transgender
                            }
                            else -> {
                                Gender.Gay
                            }
                        }

                        user.nickname = nicknameInput.text.toString()
                        user.gender = genderValue
                        user.image = url

                        database.child("users").child(auth.uid.toString()).setValue(user)
                        finish()
                    } else {
                        Toast.makeText(this@ProfileEditActivity, "모든 칸을 입력해주세요", Toast.LENGTH_SHORT).show()
                    }

                }
            }.addOnFailureListener{
                Toast.makeText(this@ProfileEditActivity, "에러", Toast.LENGTH_SHORT).show()
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