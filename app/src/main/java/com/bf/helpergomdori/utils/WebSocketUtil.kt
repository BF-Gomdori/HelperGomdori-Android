package com.bf.helpergomdori.utils

import android.annotation.SuppressLint
import android.util.Log
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.model.local.HelpType
import com.bf.helpergomdori.model.remote.response.HelpRequest
import com.bf.helpergomdori.model.websocket.*
import com.google.gson.Gson
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader

object WebSocketUtil {
    const val WEBSOCKET_URL =
        "ws://ec2-3-38-49-6.ap-northeast-2.compute.amazonaws.com:8080/dori"
    const val TOPIC_DEST_PATH = "/map/main"
    const val TOPIC_USER_DEST_PATH = "/user/map/main"
    const val SEND_DEST_PATH = "/gom-dori/connecting"

    private lateinit var stompClient: StompClient
    private var _currentLocation: Location? = null
    val currentLocation get() = _currentLocation


    fun runStomp() {
        connectStompClient()

        receiveExistedMessage()
        receiveNewMessage()

        observeStompClient()
    }


    /**
     * INTERNAL PROCESS
     */
    private fun connectStompClient() {
        try {
            stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, WEBSOCKET_URL)

            val jwt = PrefsUtil.getWebSocketJwt()

            val headerList = mutableListOf<StompHeader>().apply {
                add(StompHeader(WEBSOCKET_HEADER, jwt))
            }
            stompClient.connect(headerList)
        } catch (e: Exception) {
            Log.e(WEBSOCKET_TAG, "stompclient: ${e.printStackTrace()}")
            return
        }
    }

    @SuppressLint("CheckResult")
    private fun receiveExistedMessage() {
        try {
            val userTopic = stompClient.topic(TOPIC_USER_DEST_PATH)
            userTopic.subscribe {
                Log.i(WEBSOCKET_TAG, "receiveExistedMessage: ${it?.payload}")
                val startIndex = it.payload.indexOf("{")
                val endIndex = it.payload.indexOf("}")
                if (startIndex != -1 && endIndex != -1) {
                    val data = it.payload.substring(startIndex, endIndex)
                    Log.i(WEBSOCKET_TAG, "user topic data: ${data}")
                }
            }
        } catch (e: Exception) {
            Log.e(WEBSOCKET_TAG, "receiveExistedMessage: ${e.printStackTrace()}")
            return
        }
    }

    @SuppressLint("CheckResult")
    private fun receiveNewMessage() {
        try {
            val topic = stompClient.topic(TOPIC_DEST_PATH)
            //Log.i(WEBSOCKET_TAG, "runStomp: ${topic}")
            topic.subscribe {
                Log.i(WEBSOCKET_TAG, "receiveNewMessage: ${it?.payload}")

            }
        } catch (e: Exception) {
            Log.e(WEBSOCKET_TAG, "receiveNewMessage: ${e.printStackTrace()}")
            return
        }
    }

    private fun sendEnterMessage() {
        if (currentLocation != null && this::stompClient.isInitialized) {
            val data = WebSocketSendData(
                type = EnterType.ENTER.name,
                jwt = PrefsUtil.getWebSocketJwt(),
                location = currentLocation!!
            )
            Log.d(WEBSOCKET_TAG, "sendEnterMessage = ${Gson().toJson(data)}")
            stompClient.send(SEND_DEST_PATH, Gson().toJson(data)).subscribe()
        }
    }

    private fun checkHelpType(): HelpType {
        return PrefsUtil.getHelpType()
    }


    @SuppressLint("CheckResult")
    private fun observeStompClient() {
        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i(WEBSOCKET_TAG, "OPEND!!")
                    if (checkHelpType() == HelpType.BF) {
                        sendEnterMessage()
                    }
                    Log.i(WEBSOCKET_TAG, "RECEIVE : ${lifecycleEvent.message}")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.i(WEBSOCKET_TAG, "CLOSED!!")

                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i(WEBSOCKET_TAG, "ERROR!!")
                    Log.e(WEBSOCKET_TAG, "CONNECT ERROR : ${lifecycleEvent.exception}")
                    return@subscribe
                }
                else -> {
                    Log.i(WEBSOCKET_TAG, "SUCCESS : ${lifecycleEvent.message}")
                }
            }
        }
    }


    /**
     * EXTERNAL PROCESS
     */
    fun setCurrentLocation(location: Location) {
        _currentLocation = location
    }

    fun sendHelpMessage(helpRequest: HelpRequest) {
        if (currentLocation != null && this::stompClient.isInitialized) {
            val data = WebSocketSendData(
                type = EnterType.HELP.name,
                jwt = PrefsUtil.getWebSocketJwt(),
                location = currentLocation!!,
                helpRequest = helpRequest
            )
            Log.d(WEBSOCKET_TAG, "sendHelpMessage = ${Gson().toJson(data)}")
            stompClient.send(SEND_DEST_PATH, Gson().toJson(data)).subscribe()
        }
    }

}