package com.superx.heroes.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hero(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    @Embedded val powerstats: Powerstats,
    val fullName: String,
    val placeOfBirth: String?,
    val occupation: String?,
    val avatarUrl: String,
    val isBookmarked: Boolean = false,
)
