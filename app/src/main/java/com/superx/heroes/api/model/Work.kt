package com.superx.heroes.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Work(
    @Json(name = "occupation")
    val occupation: String?,
    @Json(name = "base")
    val base: String?
)