package com.bf.helpergomdori.model

abstract class Profile(
    open val jwt: String,
    open val type: String? = "베:프",
    open val name: String,
    open val age: Int,
    open val gender: Gender,
    open val img: String? = null,
    open val location: String,
    open val latitude: Double,
    open val longitude: Double
)

data class ProfileBf(
    override val jwt: String,
    override val type: String? = "베:프",
    override val name: String,
    override val age: Int,
    override val gender: Gender,
    override val img: String? = null,
    override val location: String,
    override val latitude: Double,
    override val longitude: Double
): Profile(jwt, type, name, age, gender, img, location, latitude, longitude)

data class ProfileGomdori(
    override val jwt: String,
    override val type: String,
    override val name: String,
    override val age: Int,
    override val gender: Gender,
    override val img: String? = null,
    val requested_term: String,
    override val location: String,
    override val latitude: Double,
    override val longitude: Double
): Profile(jwt, type, name, age, gender, img, location, latitude, longitude)

enum class Gender {
    MALE, FEMALE, NONE
}