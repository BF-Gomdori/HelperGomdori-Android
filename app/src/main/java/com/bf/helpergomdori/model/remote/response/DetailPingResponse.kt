package com.bf.helpergomdori.model.remote.response


import com.bf.helpergomdori.model.websocket.HelpRequest
import java.io.Serializable


abstract class DetailPingResponse(
    open val age: String,
    open val gender: String,
    open val location: String,
    open val name: String,
    open val phone: String,
    open val photoLink: String
) : Serializable

data class HelperDetailPing(
    val age: String,
    val gender: String,
    val helperInfo: HelperInfo,
    val location: String,
    val name: String,
    val phone: String,
    val photoLink: String
) : Serializable

data class HelperInfo(
    val averageRate: Double,
    val hearts: Int,
    val helpCount: Int
) : Serializable

data class HelpeeDetailPing(
    val age: String,
    val gender: String,
    val helpRequest: HelpRequest,
    val location: String,
    val name: String,
    val phone: String,
    val photoLink: String,
    val type: String
) : Serializable

