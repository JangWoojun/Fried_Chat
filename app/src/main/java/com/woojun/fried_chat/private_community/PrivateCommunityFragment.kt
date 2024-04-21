package com.woojun.fried_chat.private_community

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.woojun.fried_chat.databinding.FragmentPrivateCommunityBinding
import com.woojun.fried_chat.public_community.PublicCommunityAdapter
import com.woojun.fried_chat.public_community.PublicFeed

class PrivateCommunityFragment : Fragment() {

    private var _binding: FragmentPrivateCommunityBinding? = null
    private val binding get() = _binding!!
    private var list = mutableListOf<PrivateFeed>()

    private var tag = Gender.Gay

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivateCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference
        auth = Firebase.auth

        binding.apply {
            writeButton.setOnClickListener {
                startActivity(
                    Intent(requireContext(), PrivateWriteActivity::class.java).apply {
                    putExtra("tag", tag.toString())
                })
            }

            database.child("private").get().addOnSuccessListener {
                list = it.children.map {
                    Log.d("확인", it.getValue(PrivateFeed::class.java)!!.toString())
                    it.getValue(PrivateFeed::class.java)!!
                }.toMutableList()

                feedRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
                feedRecyclerView.adapter = PrivateCommunityAdapter(list)
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
                startActivity(
                    Intent(requireContext(), PrivateWriteActivity::class.java).apply {
                        putExtra("tag", tag.toString())
                    })
            }

            database.child("private").get().addOnSuccessListener {
                list = it.children.map {
                    Log.d("확인", it.getValue(PrivateFeed::class.java)!!.toString())
                    it.getValue(PrivateFeed::class.java)!!
                }.toMutableList()

                feedRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
                feedRecyclerView.adapter = PrivateCommunityAdapter(list)
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