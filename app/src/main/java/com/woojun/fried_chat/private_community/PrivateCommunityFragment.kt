package com.woojun.fried_chat.private_community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.woojun.fried_chat.R
import com.woojun.fried_chat.databinding.FragmentChatBinding
import com.woojun.fried_chat.databinding.FragmentPrivateCommunityBinding

class PrivateCommunityFragment : Fragment() {

    private var _binding: FragmentPrivateCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivateCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}