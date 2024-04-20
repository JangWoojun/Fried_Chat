package com.woojun.fried_chat.chat

data class Chat(
    val massage: String = "",
    val isUser: Boolean = true,
    var viewShow: Boolean = true
)