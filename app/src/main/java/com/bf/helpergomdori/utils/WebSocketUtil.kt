package com.bf.helpergomdori.utils

import android.annotation.SuppressLint
import android.util.Log
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.model.websocket.EnterType
import com.bf.helpergomdori.model.websocket.Location
import com.bf.helpergomdori.model.websocket.WebSocketData
import com.google.gson.Gson
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

        val jwt = PrefsUtil.getJwt()

        val headerList = mutableListOf<StompHeader>().apply {
            add(StompHeader(WEBSOCKET_HEADER, jwt))
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


        val websocketData =WebSocketData(
            type = EnterType.ENTER.name,
            jwt = PrefsUtil.getJwt(),
            location = JSONObject().apply {
                put("x", "126.9599375")
                put("y", "37.496187500000005")
            }
        )



        stompClient.send(SEND_DEST_PATH, Gson().toJson(websocketData)).subscribe()
    }

}