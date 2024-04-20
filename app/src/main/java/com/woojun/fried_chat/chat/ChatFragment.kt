package com.woojun.fried_chat.chat

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.woojun.fried_chat.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val chatList = mutableListOf<Chat>()
    private val adapter = ChattingAdapter(chatList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            binding.chatRecycler.layoutManager = LinearLayoutManager(requireContext())
            binding.chatRecycler.adapter = adapter

            adapter.addChat(
                Chat("안녕하세요!\n" +
                        "저는 성소수자 정신 건강 케어\n" +
                        "서비스 프라이드 챗의 도우미,\n" +
                        "보우라고 합니다!\n" +
                        "\n" +
                        "어떤 고민이 있으신가요?\n" +
                        "저에게 말해주세요!\n" +
                        "(닉네임)님의 고민을 들어드릴게요!", false)
            )

            sendButton.setOnClickListener {
                if (binding.input.text.isNotEmpty()) {
                    adapter.addChat(Chat(binding.input.text.toString(), true))
                    binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)
                    binding.input.text.clear()
                }
            }

            button1.setOnClickListener {
                adapter.addChat(Chat("사람 많은 게이바 추천해줘", true))
                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)
                binding.input.text.clear()
            }

            button2.setOnClickListener {
                adapter.addChat(Chat("아웃팅 당했을때 어떻게 대처해?", true))
                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)
                binding.input.text.clear()
            }

            button3.setOnClickListener {
                adapter.addChat(Chat("부모님께 커밍아웃 하는 방법", true))
                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)
                binding.input.text.clear()
            }

            button4.setOnClickListener {
                adapter.addChat(Chat("동성애자 결혼 합법인 나라", true))
                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)
                binding.input.text.clear()
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}