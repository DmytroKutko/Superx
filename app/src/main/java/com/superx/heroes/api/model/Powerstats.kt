package com.superx.heroes.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Powerstats(
    @Json(name = "intelligence")
    val intelligence: Int,
    @Json(name = "strength")
    val strength: Int,
    @Json(name = "speed")
    val speed: Int,
    @Json(name = "durability")
    val durability: Int,
    @Json(name = "power")
    val power: Int,
    @Json(name = "combat")
    val combat: Int
)