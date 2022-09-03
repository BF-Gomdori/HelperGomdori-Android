package com.bf.helpergomdori.model.remote.body

data class PostUser(
    var access_token: String? = "",
    var phone: String? = "",
    var name: String? = "",
    var fcm_token: String? = ""
)