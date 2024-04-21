package com.woojun.fried_chat.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.woojun.fried_chat.SignUpActivity
import com.woojun.fried_chat.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference
        auth = Firebase.auth

        binding.apply {
            database.child("users").child(auth.uid.toString()).get().addOnSuccessListener {
                val user = it.getValue(User::class.java)!!
                tag.text = user.gender.toString()
                binding.nicknameText.text = user.nickname
                Glide.with(requireContext())
                    .load(user.image)
                    .centerCrop()
                    .into(binding.image)
            }.addOnFailureListener{
                Toast.makeText(requireContext(), "에러", Toast.LENGTH_SHORT).show()
            }

            logoutButton.setOnClickListener {
                Firebase.auth.signOut()
                startActivity(Intent(requireContext(), SignUpActivity::class.java))
                requireActivity().finishAffinity()
            }

            signoutButton.setOnClickListener {
                Firebase.auth.signOut()
                startActivity(Intent(requireContext(), SignUpActivity::class.java))
                requireActivity().finishAffinity()
            }
            docsButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kakao.com/policy/privacy"))
                startActivity(intent)
            }
            editButton.setOnClickListener {
                val intent = Intent(requireContext(), ProfileEditActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        database.child("users").child(auth.uid.toString()).get().addOnSuccessListener {
            val user = it.getValue(User::class.java)!!
            binding.tag.text = user.gender.toString()
            binding.nicknameText.text = user.nickname
            Glide.with(requireContext())
                .load(user.image)
                .centerCrop()
                .into(binding.image)
        }.addOnFailureListener{
            Toast.makeText(requireContext(), "에러", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}