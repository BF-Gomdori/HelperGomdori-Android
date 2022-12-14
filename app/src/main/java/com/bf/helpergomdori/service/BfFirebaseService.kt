package com.bf.helpergomdori.service

import android.content.Intent
import android.util.Log
import com.bf.helpergomdori.model.local.MessageData
import com.bf.helpergomdori.model.remote.body.NotificationData
import com.bf.helpergomdori.utils.BROADCAST
import com.bf.helpergomdori.utils.BROADCAST_ACTION
import com.bf.helpergomdori.utils.PUSH_TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class BfFirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(PUSH_TAG, "onNewToken: ${token} ")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(PUSH_TAG, "onMessageReceived: ${message.from} ")

        if (message.notification != null) {
            Log.d(PUSH_TAG, "Message data payload : ${message.notification!!.title} ")

            val notification = message.notification!!
            try {
                val title = notification.title ?: ""
                val body = notification.body ?: ""
                val data = NotificationData(title, body)
                val intent = Intent().apply {
                    action = BROADCAST_ACTION
                    putExtra(BROADCAST, data)
                }
                sendBroadcast(intent)
            } catch (e: Exception) {
                Log.e(PUSH_TAG, "onMessageReceived: ${e.printStackTrace()}")
            }
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}