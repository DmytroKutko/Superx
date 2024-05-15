package com.superx.heroes.feature.heroes.domain.use_case

import com.superx.heroes.database.model.Hero
import com.superx.heroes.feature.heroes.domain.HeroRepository
import javax.inject.Inject

class InsertHeroes @Inject constructor(
    private val repository: HeroRepository,
) {
    suspend operator fun invoke(heroes: List<Hero>) = repository.insertHeroes(heroes)
}