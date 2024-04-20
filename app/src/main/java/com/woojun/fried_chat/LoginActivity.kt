package com.woojun.fried_chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.woojun.fried_chat.account.User
import com.woojun.fried_chat.databinding.ActivityLoginBinding
import com.woojun.fried_chat.private_community.Gender

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.apply {
            moveButton.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                finish()
            }

            finishButton.setOnClickListener {
                if (emailInput.text.isNotEmpty() && passwordInput.text.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                        .addOnCompleteListener(this@LoginActivity) { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, "오류 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this@LoginActivity, "모든 칸을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }
}