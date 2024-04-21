package com.woojun.fried_chat.public_community

import com.woojun.fried_chat.private_community.FeedChat

data class PublicFeed(
    val nickname: String? = null,
    val image: String? = null,
    val feedImage: String? = null,
    val feedText: String = "",
    var like: Int = 0,
    val chat: Int = 0,
    var touch: Boolean = false,
    val chatList: MutableList<FeedChat> = mutableListOf()
)
