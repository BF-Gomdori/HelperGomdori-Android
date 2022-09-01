package com.bf.helpergomdori.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bf.helpergomdori.R
import java.util.*

object NotificationUtil {
    lateinit var notificationManager: NotificationManager
    lateinit var applicationContext: Context

    private val CHANNEL_ID = "NotificationChannel"
    private val CHANNEL_NAME = "Push"


    fun setDefault(context: Context, mNotificationManager: NotificationManager) {
        applicationContext = context
        notificationManager = mNotificationManager
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(title: String, body: String) {
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        val notiId = Random().nextInt()
        notificationManager.notify(notiId, builder.build())

    }

}