package com.superx.heroes.feature.home.domain.use_case

import com.superx.heroes.feature.home.domain.HeroRepository
import javax.inject.Inject

class GetHeroById @Inject constructor(
    private val repository: HeroRepository,
) {
    operator fun invoke(id: Int) = repository.getHeroById(id)
}