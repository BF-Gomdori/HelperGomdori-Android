package com.bf.helpergomdori.model.local

import java.io.Serializable

data class MessageData(
    var title: String?,
    var body: String?
): Serializable

data class ChannelData(
    val channelId: String,
    val channelName: String,
    val importance: Int,
    val description: String = "",
    val isLight: Boolean = false,
    val isVibration: Boolean = false
)