package com.superx.heroes.feature.home.presentation

import androidx.lifecycle.ViewModel
import com.superx.heroes.database.model.Hero
import com.superx.heroes.feature.home.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _criminalsList = MutableStateFlow<List<Hero>>(emptyList())
    val criminalsList = _criminalsList.asStateFlow()
}