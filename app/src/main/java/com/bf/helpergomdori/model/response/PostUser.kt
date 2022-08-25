package com.bf.helpergomdori.model.response

data class PostUser(
    val access_token: String,
    val phone: String,
    val name: String,
    val intro: String,
    val age: Int
)