package com.superx.heroes.feature.heroes.domain

import com.superx.heroes.api.model.HeroDtoItem
import retrofit2.Response

interface RemoteHeroRepository {
    suspend fun getAllHeroes(): Response<List<HeroDtoItem>>
}