package com.woojun.fried_chat.account

import com.woojun.fried_chat.private_community.Gender


data class User(
    var nickname: String = "",
    var gender: Gender = Gender.Gay,
    var image: String = ""
)
