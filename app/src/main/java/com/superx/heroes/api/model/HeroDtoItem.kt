package com.superx.heroes.api.model


import com.superx.heroes.database.model.Hero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeroDtoItem(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "slug")
    val slug: String?,
    @Json(name = "powerstats")
    val powerstats: Powerstats,
    @Json(name = "appearance")
    val appearance: Appearance,
    @Json(name = "biography")
    val biography: Biography,
    @Json(name = "work")
    val work: Work,
    @Json(name = "connections")
    val connections: Connections,
    @Json(name = "images")
    val images: Images
)


fun List<HeroDtoItem>.toDomain(): List<Hero> {
    return map {
        Hero(
            id = it.id,
            name = it.name,
            powerstats = com.superx.heroes.database.model.Powerstats(
                intelligence = it.powerstats.intelligence,
                strength = it.powerstats.strength,
                speed = it.powerstats.speed,
                durability = it.powerstats.durability,
                power = it.powerstats.power,
                combat = it.powerstats.combat,
            ),
            fullName = it.biography.fullName,
            placeOfBirth = it.biography.placeOfBirth,
            occupation = it.work.occupation,
            avatarUrl = it.images.md,
            isBookmarked = false
        )
    }
}