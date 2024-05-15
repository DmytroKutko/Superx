package com.superx.heroes.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
    @Json(name = "xs")
    val xs: String,
    @Json(name = "sm")
    val sm: String,
    @Json(name = "md")
    val md: String,
    @Json(name = "lg")
    val lg: String
)