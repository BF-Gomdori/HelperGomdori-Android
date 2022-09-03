package com.bf.helpergomdori.model.websocket

import java.io.Serializable

data class WebSocketSendData(
    val type: String,
    val sub: String? = "main",
    val jwt: String,
    val location: Location,
    val helpRequest: HelpRequest? = null,
):Serializable

data class WebSocketReceiveData(
    val type: String,
    val sub: String,
    val jwt: String,
    val location: Location,
    val helpRequest: HelpRequest,
    val time: String
)

data class HelpRequest(
    val detailLocation: String,
    val helpeeJwt: String,
    val requestDetail: String,
    val requestType: String
)

enum class EnterType{
    ENTER, HELP, ACCEPT // ENTER: 비장애인, HELP : 장애인, ACCPET : 도움 수락했을 때
}

data class Location(
    var x: Double,
    var y: Double
): Serializable

