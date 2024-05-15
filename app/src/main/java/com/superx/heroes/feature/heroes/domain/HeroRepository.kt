package com.superx.heroes.feature.heroes.domain

import com.superx.heroes.database.model.Hero
import kotlinx.coroutines.flow.Flow

interface HeroRepository {

    suspend fun insertHeroes(heroes: List<Hero>)

    suspend fun updateHero(hero: Hero)

    fun getHeroById(id: Int): Flow<Hero>

    fun getAllHeroes(): Flow<List<Hero>>
}