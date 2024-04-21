package com.woojun.fried_chat.private_community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.woojun.fried_chat.R
import com.woojun.fried_chat.chat.Chat
import com.woojun.fried_chat.databinding.FeedChatItemBinding
import com.woojun.fried_chat.databinding.PrivateFeedItemBinding

class FeedChatAdapter(private val feedChats: MutableList<FeedChat>): RecyclerView.Adapter<FeedChatAdapter.FeedChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedChatViewHolder {
        val binding = FeedChatItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return FeedChatViewHolder(binding).also { handler ->
            binding.apply {

            }
        }

    }

    override fun onBindViewHolder(holder: FeedChatViewHolder, position: Int) {
        holder.bind(feedChats[position])
    }

    override fun getItemCount(): Int {
        return feedChats.size
    }

    fun addChat(chat: FeedChat) {
        val position = feedChats.size
        feedChats.add(chat)

        if (position > 0) {
            notifyItemRangeChanged(position - 1, 1)
        } else {
            notifyItemInserted(feedChats.size - 1)
        }
    }


    class FeedChatViewHolder(private val binding: FeedChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feedChat: FeedChat) {
            binding.apply {
                nicknameText.text = feedChat.nickname
                text.text = feedChat.text
                Glide.with(binding.root.context)
                    .load(feedChat.image)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888))
                    .into(imageView)
            }
        }
    }

}
