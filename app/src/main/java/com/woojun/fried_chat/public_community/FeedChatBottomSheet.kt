package com.woojun.fried_chat.public_community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.woojun.fried_chat.account.User
import com.woojun.fried_chat.databinding.ChatBottomsheetBinding
import com.woojun.fried_chat.private_community.FeedChat
import com.woojun.fried_chat.private_community.FeedChatAdapter

class FeedChatBottomSheet(chatList: MutableList<FeedChat>) : BottomSheetDialogFragment()  {
    lateinit var binding: ChatBottomsheetBinding

    private val adapter = FeedChatAdapter(chatList)
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ChatBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference
        auth = Firebase.auth

        binding.apply {
            chatRecycler.layoutManager = LinearLayoutManager(requireContext().applicationContext)
            chatRecycler.adapter = adapter

            database.child("users").child(auth.uid.toString()).get().addOnSuccessListener {
                val user = it.getValue(User::class.java)!!
                sendButton.setOnClickListener {
                    adapter.addChat(FeedChat(user.nickname, user.image, input.text.toString()))
                    input.text.clear()
                }
            }.addOnFailureListener{
                Toast.makeText(requireContext(), "에러", Toast.LENGTH_SHORT).show()
            }
        }
    }

}