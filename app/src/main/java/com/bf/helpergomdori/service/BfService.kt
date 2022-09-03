package com.bf.helpergomdori.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.os.Message
import com.bf.helpergomdori.model.remote.body.MessageData
import com.bf.helpergomdori.model.remote.body.NotificationData
import com.bf.helpergomdori.utils.BROADCAST
import com.bf.helpergomdori.utils.BROADCAST_ACTION
import com.bf.helpergomdori.utils.NotificationUtil

class BfService: Service() {
    private var mBinder: IBinder? = LocalBinder()

    inner class LocalBinder : Binder() {
        val service: BfService
            get() = this@BfService
    }

    override fun onBind(intent: Intent?): IBinder? {
        val intentFilter = IntentFilter(BROADCAST_ACTION)
        applicationContext.registerReceiver(broadCastReceiver, intentFilter)
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        mBinder = null
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinder = null
        applicationContext.unregisterReceiver(broadCastReceiver)
    }

    /**
     * BroadCast Receiver
     */
    private val broadCastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action.equals(BROADCAST_ACTION)) {
                val notification = intent.getSerializableExtra(BROADCAST) as NotificationData

                NotificationUtil.sendNotification(notification.title, notification.body)
            }
        }

    }
}