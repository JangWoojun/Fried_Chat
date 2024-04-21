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
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.dialogflow.v2.QueryInput
import com.google.cloud.dialogflow.v2.QueryResult
import com.google.cloud.dialogflow.v2.SessionName
import com.google.cloud.dialogflow.v2.SessionsClient
import com.google.cloud.dialogflow.v2.SessionsSettings
import com.google.cloud.dialogflow.v2.TextInput
import com.woojun.fried_chat.R
import com.woojun.fried_chat.databinding.FragmentChatBinding
import java.util.UUID


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
        val sessionId = UUID.randomUUID().toString()

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
                        "고민을 들어드릴게요!", false)
            )

            sendButton.setOnClickListener {
                if (binding.input.text.isNotEmpty()) {
                    adapter.addChat(Chat(binding.input.text.toString(), true))
                    binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)

                    val response = sendQuery(binding.input.text.toString(), sessionId)
                    val fulfillmentText = response.fulfillmentText

                    adapter.addChat(Chat(fulfillmentText, false))
                    binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)

                    binding.input.text.clear()
                }
            }

            button1.setOnClickListener {
                adapter.addChat(Chat("사람 많은 게이바 추천해줘", true))
                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)

                val response = sendQuery("사람 많은 게이바 추천해줘", sessionId)
                val fulfillmentText = response.fulfillmentText
                adapter.addChat(Chat(fulfillmentText, false))

                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)
                binding.input.text.clear()
            }

            button2.setOnClickListener {
                adapter.addChat(Chat("아웃팅 당했을때 어떻게 대처해?", true))
                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)

                val response = sendQuery("아웃팅 당했을때 어떻게 대처해?", sessionId)
                val fulfillmentText = response.fulfillmentText
                adapter.addChat(Chat(fulfillmentText, false))

                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)
                binding.input.text.clear()
            }

            button3.setOnClickListener {
                adapter.addChat(Chat("부모님께 커밍아웃 하는 방법", true))
                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)

                val response = sendQuery("부모님께 커밍아웃 하는 방법", sessionId)
                val fulfillmentText = response.fulfillmentText
                adapter.addChat(Chat(fulfillmentText, false))

                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)
                binding.input.text.clear()
            }

            button4.setOnClickListener {
                adapter.addChat(Chat("동성애자 결혼 합법인 나라", true))
                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)

                val response = sendQuery("동성애자 결혼 합법인 나라", sessionId)
                val fulfillmentText = response.fulfillmentText
                adapter.addChat(Chat(fulfillmentText, false))

                binding.chatRecycler.scrollToPosition(adapter.getChat().size - 1)
                binding.input.text.clear()
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendQuery(query: String, sessionId: String): QueryResult {
        val projectId = "newagent-gtxp"
        val credentials = GoogleCredentials.fromStream(resources.openRawResource(R.raw.credentials))
        val settings = SessionsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build()
        val sessionsClient = SessionsClient.create(settings)

        val session = SessionName.of(projectId, sessionId)
        val textInput = TextInput.newBuilder().setText(query).setLanguageCode("ko")
        val queryInput = QueryInput.newBuilder().setText(textInput).build()
        val response = sessionsClient.detectIntent(session, queryInput)
        return response.queryResult
    }

}