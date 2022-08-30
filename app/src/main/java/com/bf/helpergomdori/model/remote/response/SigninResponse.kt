package com.bf.helpergomdori.model.remote.response

data class HelpeeResponse(
    val averageRate: Double,
    val hearts: Int,
    val id: Int,
    val requestCount: Int,
    val type: String
)

data class HelperResponse(
    val averageRate: Double,
    val hearts: Int,
    val helpCount: Int,
    val id: Int,
)
