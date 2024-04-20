package com.woojun.fried_chat.account

import com.woojun.fried_chat.private_community.Gender


data class User(
    val nickname: String,
    val gender: Gender
)
