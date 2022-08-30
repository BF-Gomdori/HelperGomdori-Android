package com.bf.helpergomdori.model.body

data class PostUser(
    val access_token: String,
    val phone: String,
    val name: String,
    val intro: String,
    val age: Int
)