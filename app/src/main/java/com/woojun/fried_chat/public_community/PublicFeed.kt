package com.woojun.fried_chat.public_community

data class PublicFeed(
    val nickname: String?,
    val image: String?,
    val feedImage: String?,
    val feedText: String,
    val like: Int,
    val chat: Int
)
