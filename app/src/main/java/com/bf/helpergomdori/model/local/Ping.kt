package com.bf.helpergomdori.model.local

import com.bf.helpergomdori.model.remote.response.DetailPingResponse
import com.bf.helpergomdori.model.remote.response.HelpeeDetailPing
import com.bf.helpergomdori.model.remote.response.HelperDetailPing
import com.bf.helpergomdori.model.websocket.Location

data class Ping(
    val type: HelpType,
    val location: Location,
    val time: String? = "",
    val jwt: String
)

data class GomdoriDetailPing(
    val helpeeDetailPing: HelpeeDetailPing,
    val latitude: Double,
    val longitude: Double
)

data class BfDetailPing(
    val helperDetailPing: HelperDetailPing,
    val latitude: Double,
    val longitude: Double
)

enum class Gender {
    MALE, FEMALE, NONE
}

enum class HelpType {
    BF, GOMDORI, NONE
}