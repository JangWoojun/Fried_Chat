package com.woojun.fried_chat

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.woojun.fried_chat.account.User
import com.woojun.fried_chat.databinding.ActivitySignUpBinding
import com.woojun.fried_chat.private_community.Gender

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var gender = ""
    private val genderList = arrayOf(
        "Gay",
        "Lesbian",
        "Transgender",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.apply {
            genderBox.setOnClickListener {
                val dialog = AlertDialog.Builder(this@SignUpActivity)
                dialog.setTitle("Gender")
                    .setItems(genderList) { _, index ->
                        gender = when (index) {
                            0 -> "g"
                            1 -> "l"
                            2 -> "t"
                            else -> "g"
                        }

                    }
                dialog.show()
            }

            finishButton.setOnClickListener {
                if (emailInput.text.isNotEmpty() && passwordInput.text.isNotEmpty() && passwordCheckInput.text.isNotEmpty() && gender.isNotEmpty() && nicknameInput.text.isNotEmpty()) {
                    if (passwordInput.text == passwordCheckInput.text) {
                        val user = when(gender) {
                            "g" -> {
                                User(nicknameInput.text.toString(), Gender.Gay)
                            }
                            "l" -> {
                                User(nicknameInput.text.toString(), Gender.Lesbian)
                            }
                            "t" -> {
                                User(nicknameInput.text.toString(), Gender.Transgender)
                            }
                            else -> {
                                User(nicknameInput.text.toString(), Gender.Gay)
                            }
                        }

                        auth.createUserWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                            .addOnCompleteListener(this@SignUpActivity) { task ->
                                if (task.isSuccessful) {
                                    database.child("users").child(auth.uid.toString()).setValue(user)
                                    startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this@SignUpActivity, "오류 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this@SignUpActivity, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@SignUpActivity, "모든 칸을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }

            moveButton.setOnClickListener {
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}