package com.superx.heroes.feature.heroes.data

import com.superx.heroes.api.HeroesApi
import com.superx.heroes.api.model.HeroDtoItem
import com.superx.heroes.feature.heroes.domain.RemoteHeroRepository
import retrofit2.Response
import javax.inject.Inject

class RemoteHeroRepositoryImpl @Inject constructor(
    private val api: HeroesApi,
) : RemoteHeroRepository {

    override suspend fun getAllHeroes(): Response<List<HeroDtoItem>> = api.getHeroesList()
}