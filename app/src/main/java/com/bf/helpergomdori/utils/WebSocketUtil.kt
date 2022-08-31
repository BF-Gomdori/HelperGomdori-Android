package com.bf.helpergomdori.utils

import android.annotation.SuppressLint
import android.util.Log
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader

object WebSocketUtil {
    private const val WEBSOCKET_URL =
        "ws://ec2-3-38-49-6.ap-northeast-2.compute.amazonaws.com:8080/dori"
    private const val TOPIC_DEST_PATH = "/map/main"
    private const val SEND_DEST_PATH = "/gom-dori/connecting"

    @SuppressLint("CheckResult")
    fun runStomp() {
        val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, WEBSOCKET_URL)

        val jwt =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLsnKDsirnrr7wiLCJpYXQiOjE2NjE5MzEwNDMsImV4cCI6MTY2MjAxNzQ0M30.MpJCboZBoLP1YE14y9WkHJHg-jMPe2Ue-SKfvUDFIdLYWmwBKypZTHRdyJz4HD_kVpgEzRr0GYsp1D2puGCFeQ"

        val headerList = mutableListOf<StompHeader>().apply {
            add(StompHeader("Authorization", jwt))
        }
        stompClient.connect(headerList)

        try {
            val topic = stompClient.topic(TOPIC_DEST_PATH)
            Log.i(WEBSOCKET_TAG, "runStomp: ${topic}")

            topic.subscribe {
                Log.i(WEBSOCKET_TAG, "runStomp: ${it?.payload}")
            }
        } catch (e: NullPointerException) {
            Log.e(WEBSOCKET_TAG, "runStomp: ${e.printStackTrace()}")
            return
        }


        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i(WEBSOCKET_TAG, "OPEND!!")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.i(WEBSOCKET_TAG, "CLOSED!!")

                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i(WEBSOCKET_TAG, "ERROR!!")
                    Log.e(WEBSOCKET_TAG, "CONNECT ERROR : $lifecycleEvent.exception.toString()")
                }
                else -> {
                    Log.i(WEBSOCKET_TAG, "SUCCESS : ${lifecycleEvent.message}")
                }
            }
        }

        val data = JSONObject().apply {
            put("type", "ENTER") // ENTER, HELP, ACCEPT
            put("sub", "main")
            put("jwt", jwt)
            put("location", JSONObject().apply {
                put("x", "11111")
                put("y", "11111")
            })
        }

        stompClient.send(SEND_DEST_PATH, data.toString()).subscribe()
    }

}