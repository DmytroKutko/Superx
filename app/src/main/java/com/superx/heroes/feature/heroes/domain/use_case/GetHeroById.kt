package com.superx.heroes.feature.heroes.domain.use_case

import com.superx.heroes.feature.heroes.domain.HeroRepository
import javax.inject.Inject

class GetHeroById @Inject constructor(
    private val repository: HeroRepository,
) {
    operator fun invoke(id: Int) = repository.getHeroById(id)
}