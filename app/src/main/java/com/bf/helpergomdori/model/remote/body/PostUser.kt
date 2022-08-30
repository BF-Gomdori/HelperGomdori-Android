package com.bf.helpergomdori.model.remote.body

data class PostUser(
    var access_token: String? = "",
    var phone: String? = "",
    var name: String? = "",
    var intro: String? = "",
    var age: Int? = 0
)