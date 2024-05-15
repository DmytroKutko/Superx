package com.superx.heroes.api

import com.superx.heroes.api.model.HeroDtoItem
import retrofit2.Response
import retrofit2.http.GET

interface HeroesApi {
    @GET("/gh/akabab/superhero-api@0.3.0/api/all.json")
    suspend fun getHeroesList(): Response<List<HeroDtoItem>>
}