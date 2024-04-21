package com.woojun.fried_chat.public_community

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.woojun.fried_chat.R
import com.woojun.fried_chat.databinding.PublicFeedItemBinding
import com.woojun.fried_chat.private_community.FeedChatAdapter

class PublicCommunityAdapter(private val feeds: MutableList<PublicFeed>): RecyclerView.Adapter<PublicCommunityAdapter.PublicCommunityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicCommunityViewHolder {
        val binding = PublicFeedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PublicCommunityViewHolder(binding).also { handler ->
            binding.apply {
                likeButton.setOnClickListener {
                    val feed = feeds[handler.position]
                    if (!feed.touch) {
                        feeds[handler.position].like = feed.like+1
                        likeText.text = "${feed.like + 1} 개"
                        likeImage.setImageResource(R.drawable.select_like_icon)
                        feeds[handler.position].touch = !feed.touch
                    } else {
                        feeds[handler.position].like = feed.like-1
                        likeText.text = "${feed.like - 1} 개"
                        likeImage.setImageResource(R.drawable.like_icon)
                        feeds[handler.position].touch = !feed.touch
                    }
                    notifyDataSetChanged()
                }
                chatButton.setOnClickListener {
                    val chatBottomSheet = FeedChatBottomSheet(feeds[handler.position].chatList)
                    chatBottomSheet.show((binding.root.context as AppCompatActivity).supportFragmentManager, chatBottomSheet.tag)
                }
            }
        }

    }

    override fun onBindViewHolder(holder: PublicCommunityViewHolder, position: Int) {
        holder.bind(feeds[position])
    }

    override fun getItemCount(): Int {
        return feeds.size
    }


    class PublicCommunityViewHolder(private val binding: PublicFeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: PublicFeed) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(feed.image)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888))
                    .into(image)
                Glide.with(binding.root.context)
                    .load(feed.feedImage)
                    .apply(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888))
                    .into(feedImage)
                nicknameText.text = feed.nickname
                feedText.text = feed.feedText
                likeText.text = "${feed.like}개"
                chatText.text = "${feed.chat}개"
            }
        }
    }

}
