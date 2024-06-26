package com.superx.heroes.feature.home.domain.use_case

import com.superx.heroes.api.model.toDomain
import com.superx.heroes.database.model.Hero
import com.superx.heroes.feature.home.domain.HeroRepository
import com.superx.heroes.feature.home.domain.RemoteHeroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllHeroes @Inject constructor(
    private val repository: HeroRepository,
    private val remoteHeroRepository: RemoteHeroRepository,
) {
    suspend operator fun invoke(): Flow<List<Hero>> = flow {

        val heroesList = repository.getAllHeroes().first()

        if (heroesList.isEmpty()) {

            val response = remoteHeroRepository.getAllHeroes()

            if (response.isSuccessful) {
                val responseList = response.body()?.toDomain() ?: emptyList()
                repository.insertHeroes(responseList)
                emit(responseList)
            } else {
                emit(emptyList())
            }
        } else {
            emit(heroesList)
        }
    }
}