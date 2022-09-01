package com.bf.helpergomdori.model.remote.response

abstract class Ping(
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
): Ping(age, gender, name, phone, photoLink, location)

data class HelpRequest(
    val detailLocation: String,
    val helpeeJwt: String,
    val requestDetail: String,
    val requestType: String
)

data class HelperPing(
    override val age: String,
    override val gender: String,
    val helpRequest: HelpRequest,
    override val location: String,
    override val name: String,
    override val phone: String,
    override val photoLink: String,
    val type: String
): Ping(age, gender, name, phone, photoLink, location)
