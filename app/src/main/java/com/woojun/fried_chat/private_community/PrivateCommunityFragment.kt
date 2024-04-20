package com.woojun.fried_chat.private_community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.woojun.fried_chat.databinding.FragmentPrivateCommunityBinding

class PrivateCommunityFragment : Fragment() {

    private var _binding: FragmentPrivateCommunityBinding? = null
    private val binding get() = _binding!!
    private val list = mutableListOf<PrivateFeed>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivateCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            feedRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
            feedRecyclerView.adapter = PrivateCommunityAdapter(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}