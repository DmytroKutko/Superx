package com.superx.heroes.feature.heroes.domain.use_case

import com.superx.heroes.feature.heroes.domain.use_case.GetAllHeroes
import com.superx.heroes.feature.heroes.domain.use_case.GetHeroById
import com.superx.heroes.feature.heroes.domain.use_case.InsertHeroes
import com.superx.heroes.feature.heroes.domain.use_case.UpdateHero

data class UseCases(
    val insertHeroes: InsertHeroes,
    val updateHero: UpdateHero,
    val getHeroById: GetHeroById,
    val getAllHeroes: GetAllHeroes
)
