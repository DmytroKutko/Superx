package com.superx.heroes.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Connections(
    @Json(name = "groupAffiliation")
    val groupAffiliation: String?,
    @Json(name = "relatives")
    val relatives: String?
)