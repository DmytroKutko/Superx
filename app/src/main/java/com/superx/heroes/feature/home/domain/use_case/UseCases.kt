package com.superx.heroes.feature.home.domain.use_case

data class UseCases(
    val insertHeroes: InsertHeroes,
    val updateHero: UpdateHero,
    val getHeroById: GetHeroById,
    val getAllHeroes: GetAllHeroes
)
