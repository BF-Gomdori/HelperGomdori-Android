package com.bf.helpergomdori.service

import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.os.Message
import android.util.Log
import com.bf.helpergomdori.model.remote.body.MessageData
import com.bf.helpergomdori.model.remote.body.NotificationData
import com.bf.helpergomdori.ui.main.MainActivity
import com.bf.helpergomdori.utils.BROADCAST
import com.bf.helpergomdori.utils.BROADCAST_ACTION
import com.bf.helpergomdori.utils.NotificationUtil
import com.bf.helpergomdori.utils.PUSH_TAG

class BfService: Service() {
    private var mBinder: IBinder? = LocalBinder()

    inner class LocalBinder : Binder() {
        val service: BfService
            get() = this@BfService
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(PUSH_TAG, ">>>>> BfService onCreate ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(PUSH_TAG, ">>>>> BfService onStartCommand ")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(PUSH_TAG, ">>>>> BfService onBind ")
        val intentFilter = IntentFilter(BROADCAST_ACTION)
        applicationContext.registerReceiver(broadCastReceiver, intentFilter)
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(PUSH_TAG, ">>>>> BfService unBind ")
        mBinder = null
        return true
    }

    override fun onDestroy() {
        Log.d(PUSH_TAG, ">>>>> BfService onDestroy ")
        super.onDestroy()
        mBinder = null
        applicationContext.unregisterReceiver(broadCastReceiver)
    }

    /**
     * BroadCast Receiver
     */
    private val broadCastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            Log.d(PUSH_TAG, ">>>>> BfService onReceive: ${intent.action}")
            if (intent.action.equals(BROADCAST_ACTION)) {
                NotificationUtil.createNotificationChannel()
                val notification = intent.getSerializableExtra(BROADCAST) as NotificationData
                val intentToMove = Intent(this@BfService, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
                val pendingIntent = PendingIntent.getActivity(this@BfService, 0, intentToMove, PendingIntent.FLAG_MUTABLE)
                NotificationUtil. sendNotification(notification.title, notification.body, pendingIntent)
            }
        }

    }
}