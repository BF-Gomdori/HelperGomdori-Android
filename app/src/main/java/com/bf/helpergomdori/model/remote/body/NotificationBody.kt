package com.bf.helpergomdori.model.remote.body

import java.io.Serializable

data class NotificationBody(
    val message: MessageData,
    val validate_only: Boolean? = true
): Serializable

data class MessageData(
    val notification: NotificationData,
    val token: String
)

data class NotificationData(
    val title: String = "",
    val body: String = ""
): Serializable