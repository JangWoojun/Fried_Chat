package com.woojun.fried_chat.public_community

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.woojun.fried_chat.account.User
import com.woojun.fried_chat.databinding.FragmentPublicCommunityBinding

class PublicCommunityFragment : Fragment() {

    private var _binding: FragmentPublicCommunityBinding? = null
    private val binding get() = _binding!!
    private var list = mutableListOf<PublicFeed>()

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublicCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference
        auth = Firebase.auth

        binding.apply {
            writeButton.setOnClickListener {
                startActivity(Intent(requireContext(), PublicWriteActivity::class.java))
            }
            database.child("public").get().addOnSuccessListener {
                list = it.children.map {
                    Log.d("확인", it.getValue(PublicFeed::class.java)!!.toString())
                    it.getValue(PublicFeed::class.java)!!
                }.toMutableList()

                feedRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
                feedRecyclerView.adapter = PublicCommunityAdapter(list)
            }.addOnFailureListener{
                Toast.makeText(requireContext(), "에러", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        database = Firebase.database.reference
        auth = Firebase.auth

        binding.apply {
            writeButton.setOnClickListener {
                startActivity(Intent(requireContext(), PublicWriteActivity::class.java))
            }
            database.child("public").get().addOnSuccessListener {
                list = it.children.map {
                    Log.d("확인", it.getValue(PublicFeed::class.java)!!.toString())
                    it.getValue(PublicFeed::class.java)!!
                }.toMutableList()

                feedRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
                feedRecyclerView.adapter = PublicCommunityAdapter(list)
            }.addOnFailureListener{
                Toast.makeText(requireContext(), "에러", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}