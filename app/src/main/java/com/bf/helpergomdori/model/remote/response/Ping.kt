package com.bf.helpergomdori.model.remote.response

import com.bf.helpergomdori.model.local.HelpType
import com.bf.helpergomdori.model.websocket.HelpRequest
import com.bf.helpergomdori.model.websocket.Location

data class Ping(
    val type: HelpType,
    val location: Location,
    val time: String? = ""
)

abstract class DetailPing(
    open val age: String,
    open val gender: String,
    open val name: String,
    open val phone: String,
    open val photoLink: String,
    open val location: String
)

data class HelpeePing(
    override val age: String,
    override val gender: String,
    val helpRequest: HelpRequest,
    override val location: String,
    override val name: String,
    override val phone: String,
    override val photoLink: String,
    val type: String
): DetailPing(age, gender, name, phone, photoLink, location)


data class HelperPing(
    override val age: String,
    override val gender: String,
    val helpRequest: HelpRequest,
    override val location: String,
    override val name: String,
    override val phone: String,
    override val photoLink: String,
    val type: String
): DetailPing(age, gender, name, phone, photoLink, location)
