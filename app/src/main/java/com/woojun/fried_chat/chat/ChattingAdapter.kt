package com.woojun.fried_chat.chat

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.woojun.fried_chat.R
import com.woojun.fried_chat.databinding.ChatItemBinding
import com.woojun.fried_chat.databinding.OtherChatItemBinding

class ChattingAdapter(private val chatList: MutableList<Chat>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            1 -> {
                val binding = ChatItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )

                ChatViewHolder(binding).also { handler ->
                    binding.apply {

                    }
                }
            }
            else -> {
                val binding = OtherChatItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )

                OtherChatViewHolder(binding).also { handler ->
                    binding.apply {

                    }
                }
            }

        }
    }

    fun addChat(chat: Chat) {
        val position = chatList.size
        chatList.add(chat)

        if (position > 0) {
            chatList[position - 1].viewShow = false
            notifyItemRangeChanged(position - 1, 1)
        } else {
            notifyItemInserted(chatList.size - 1)
        }
    }

    fun getChat(): List<Chat> {
        return chatList
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].isUser) 1 else 0
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is ChatViewHolder) {
            holder.init()
        } else if (holder is OtherChatViewHolder) {
            holder.init()
        }
    }

    private fun Double.fromDpToPx(): Int =
        (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatItem = chatList[position]

        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams

        if (position == 0) {
            layoutParams.setMargins(0, 22.0.fromDpToPx(), 0, 0)
            holder.itemView.layoutParams = layoutParams
        } else if (position == chatList.size - 1) {
            if (chatList[position - 1].isUser == chatItem.isUser) {
                layoutParams.setMargins(0, 5.0.fromDpToPx(), 0, 22.0.fromDpToPx())
                holder.itemView.layoutParams = layoutParams
            } else {
                layoutParams.setMargins(0, 22.0.fromDpToPx(), 0, 22.0.fromDpToPx())
                holder.itemView.layoutParams = layoutParams
            }
        } else {
            if (chatList[position - 1].isUser == chatItem.isUser) {
                layoutParams.setMargins(0, 5.0.fromDpToPx(), 0, 0)
                holder.itemView.layoutParams = layoutParams
            } else {
                layoutParams.setMargins(0, 22.0.fromDpToPx(), 0, 0)
                holder.itemView.layoutParams = layoutParams
            }
        }

        if (chatItem.isUser) {
            (holder as ChatViewHolder).bind(chatItem)
        } else {
            (holder as OtherChatViewHolder).bind(chatItem)
        }
    }

    class ChatViewHolder(private val binding: ChatItemBinding):
        ViewHolder(binding.root) {

        fun init() {
            binding.apply {
                messageText.text = ""
            }
        }
        fun bind(chat: Chat) {
            binding.apply{
                messageText.text = chat.massage
            }
        }
    }

    class OtherChatViewHolder(private val binding: OtherChatItemBinding):
        ViewHolder(binding.root) {

        fun init() {
            binding.apply {
                messageText.text = ""
                image.visibility = View.INVISIBLE
            }
        }
        fun bind(chat: Chat) {
            binding.apply{
                messageText.text = chat.massage
            }
        }
    }

}