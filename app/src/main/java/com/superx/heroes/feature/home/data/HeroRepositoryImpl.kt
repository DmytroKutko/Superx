package com.superx.heroes.feature.home.data

import com.superx.heroes.database.HeroDao
import com.superx.heroes.database.model.Hero
import com.superx.heroes.feature.home.domain.HeroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HeroRepositoryImpl @Inject constructor(
    private val dao: HeroDao
): HeroRepository {
    override suspend fun insertHeroes(heroes: List<Hero>) = dao.insertHeroesList(heroes)

    override suspend fun updateHero(hero: Hero) = dao.updateHero(hero)

    override fun getHeroById(id: Int): Flow<Hero> = dao.getHeroById(id)

    override fun getAllHeroes(): Flow<List<Hero>> = dao.getAllHeroes()
}