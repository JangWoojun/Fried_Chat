package com.woojun.fried_chat.private_community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.woojun.fried_chat.R
import com.woojun.fried_chat.databinding.PrivateFeedItemBinding

class PrivateCommunityAdapter(private val feeds: MutableList<PrivateFeed>): RecyclerView.Adapter<PrivateCommunityAdapter.PrivateCommunityViewHolder>() {
    private var tag = Gender.Gay

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrivateCommunityViewHolder {
        val binding = PrivateFeedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PrivateCommunityViewHolder(binding).also { handler ->
            binding.apply {

            }
        }

    }

    override fun onBindViewHolder(holder: PrivateCommunityViewHolder, position: Int) {
        holder.bind(feeds[position], tag, position)
    }

    override fun getItemCount(): Int {
        return feeds.size
    }


    class PrivateCommunityViewHolder(private val binding: PrivateFeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: PrivateFeed, tag: Gender, position: Int) {
            binding.apply {
                if (feed.tag == tag) {
                    Glide.with(binding.root.context)
                        .load(R.drawable.face1_icon)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888))
                        .into(image)
                } else {
                    Glide.with(binding.root.context)
                        .load(R.drawable.face2_icon)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888))
                        .into(image)
                }
                nicknameText.text = "익명$position"
                feedText.text = feed.feedText
                tagText.text = feed.tag.toString()
            }
        }
    }

}
