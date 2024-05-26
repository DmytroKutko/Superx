package com.superx.heroes.feature.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superx.heroes.database.model.Hero
import com.superx.heroes.feature.home.domain.use_case.UseCases
import com.superx.heroes.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val useCases: UseCases,
    private val updateListSharedFlow: MutableSharedFlow<Hero>,
) : ViewModel() {
    private val _response: MutableStateFlow<Response<List<Hero>>> =
        MutableStateFlow(Response.Idle)
    val response = _response.asStateFlow()

    var currentScrollPosition: Int = 0 // Variable to store the current scroll position
    var currentScrollPositionOffset: Int = 0

    init {
        getAllFavorites()
        trackUpdateEvent()
    }

    private fun trackUpdateEvent() = viewModelScope.launch {
        updateListSharedFlow.collect { hero ->
            getAllFavorites()
        }
    }

    fun getAllFavorites() = viewModelScope.launch {
        useCases.getAllHeroes()
            .onStart {
                _response.emit(Response.Loading)
            }
            .catch {
                _response.emit(Response.Error(it))
            }
            .collect { list ->
                val favorites = list.filter { it.isBookmarked }
                _response.emit(Response.Success(favorites))
            }
    }
}