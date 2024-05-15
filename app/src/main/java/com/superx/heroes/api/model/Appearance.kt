package com.superx.heroes.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Appearance(
    @Json(name = "gender")
    val gender: String,
    @Json(name = "race")
    val race: String?,
    @Json(name = "height")
    val height: List<String>,
    @Json(name = "weight")
    val weight: List<String>,
    @Json(name = "eyeColor")
    val eyeColor: String?,
    @Json(name = "hairColor")
    val hairColor: String?
)