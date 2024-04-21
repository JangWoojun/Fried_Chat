package com.woojun.fried_chat.private_community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.woojun.fried_chat.R
import com.woojun.fried_chat.databinding.ActivityPrivateWriteBinding

class PrivateWriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivateWriteBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivateWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference

        binding.apply {
            val tagValue = intent.getStringExtra("tag")
            tag.text = tagValue

            finishButton.setOnClickListener {
                val privatePost = when (tagValue) {
                    "Gay" -> PrivateFeed(Gender.Gay, input.text.toString())
                    "Lesbian" -> PrivateFeed(Gender.Lesbian, input.text.toString())
                    "Transgender" -> PrivateFeed(Gender.Transgender, input.text.toString())
                    else -> PrivateFeed(Gender.Gay, input.text.toString())
                }

                if (input.text.isNotEmpty()) {
                    database.child("private").child(System.currentTimeMillis().toString()).setValue(privatePost)
                    finish()
                } else {
                    Toast.makeText(this@PrivateWriteActivity, "사진을 넣어주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}