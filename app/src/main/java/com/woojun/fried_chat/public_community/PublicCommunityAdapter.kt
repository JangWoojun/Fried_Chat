package com.woojun.fried_chat.public_community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.woojun.fried_chat.databinding.PublicFeedItemBinding

class PublicCommunityAdapter(private val feeds: MutableList<Feed>): RecyclerView.Adapter<PublicCommunityAdapter.PublicCommunityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicCommunityViewHolder {
        val binding = PublicFeedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PublicCommunityViewHolder(binding).also { handler ->
            binding.apply {

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
        fun bind(feed: Feed) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(feed.image)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888))
                    .into(image)
                Glide.with(binding.root.context)
                    .load(feed.feedImage)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888))
                    .into(image)
                nicknameText.text = feed.nickname
                feedText.text = feed.feedText
                likeText.text = "${feed.like}개"
                chatText.text = "${feed.chat}개"
            }
        }
    }

}
