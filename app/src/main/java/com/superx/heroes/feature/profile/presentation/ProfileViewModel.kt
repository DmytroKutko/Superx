package com.superx.heroes.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superx.heroes.database.model.Hero
import com.superx.heroes.database.model.Powerstats
import com.superx.heroes.feature.heroes.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: UseCases,
    private val updateListSharedFlow: MutableSharedFlow<Hero>
): ViewModel() {
    private val _hero = MutableStateFlow(
        Hero(
            id = 0,
            name = "",
            powerstats = Powerstats(0,0,0,0,0,0),
            fullName = "",
            placeOfBirth = "",
            avatarUrl = "",
            occupation = "",
            isBookmarked = false
        )
    )
    val hero = _hero.asStateFlow()

    fun updateHero(hero: Hero) = viewModelScope.launch {
        useCases.updateHero(hero)
        updateListSharedFlow.emit(hero)
    }

    fun getHeroById(id: Int) = viewModelScope.launch {
        useCases.getHeroById(id)
            .collect {
                _hero.emit(it)
            }
    }
}