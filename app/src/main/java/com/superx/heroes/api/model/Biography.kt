package com.superx.heroes.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Biography(
    @Json(name = "fullName")
    val fullName: String,
    @Json(name = "alterEgos")
    val alterEgos: String?,
    @Json(name = "aliases")
    val aliases: List<String>,
    @Json(name = "placeOfBirth")
    val placeOfBirth: String?,
    @Json(name = "firstAppearance")
    val firstAppearance: String?,
    @Json(name = "publisher")
    val publisher: String?,
    @Json(name = "alignment")
    val alignment: String?
)