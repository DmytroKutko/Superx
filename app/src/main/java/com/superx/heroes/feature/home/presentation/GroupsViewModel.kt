package com.superx.heroes.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superx.heroes.database.model.Hero
import com.superx.heroes.feature.home.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    var currentScrollPosition: Int = 0
    var currentScrollPositionOffset: Int = 0


    private val _criminalsList = MutableStateFlow<List<Hero>>(emptyList())
    val criminalsList = _criminalsList.asStateFlow()

    private val _assassinsList = MutableStateFlow<List<Hero>>(emptyList())
    val assassinsList = _assassinsList.asStateFlow()

    private val _mercenaryList = MutableStateFlow<List<Hero>>(emptyList())
    val mercenaryList = _mercenaryList.asStateFlow()

    private val _soldiersList = MutableStateFlow<List<Hero>>(emptyList())
    val soldiersList = _soldiersList.asStateFlow()

    init {
        viewModelScope.launch {
            useCases.getAllHeroes()
                .collect { list ->
                    val criminals = list.filter { hero ->
                        hero.occupation?.contains("criminal", true) ?: false
                    }
                    _criminalsList.emit(criminals)

                    val assassins = list.filter { hero ->
                        hero.occupation?.contains("assassin", true) ?: false
                    }
                    _assassinsList.emit(assassins)

                    val mercenary = list.filter { hero ->
                        hero.occupation?.contains("mercenary", true) ?: false
                    }
                    _mercenaryList.emit(mercenary)

                    val soldiers = list.filter { hero ->
                        hero.occupation?.contains("soldier", true) ?: false
                    }
                    _soldiersList.emit(soldiers)
                }
        }
    }
}