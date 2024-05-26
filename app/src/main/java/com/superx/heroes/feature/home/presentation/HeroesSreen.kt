package com.superx.heroes.feature.home.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.superx.heroes.R
import com.superx.heroes.feature.core.presentation.ErrorScreen
import com.superx.heroes.feature.core.presentation.LoadingScreen
import com.superx.heroes.feature.home.presentation.components.HeroesList
import com.superx.heroes.util.Response
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun HeroesScreen(
    onHeroClick: (Int) -> Unit,
    viewModel: HeroesViewModel = hiltViewModel(),
) {
    val response by viewModel.response.collectAsStateWithLifecycle()

    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    LaunchedEffect(lazyStaggeredGridState) {
        val scrollState = lazyStaggeredGridState
        val onScrollPositionChanged: () -> Unit = {
            viewModel.currentScrollPosition =
                lazyStaggeredGridState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
            viewModel.currentScrollPositionOffset = Math.abs(
                lazyStaggeredGridState.layoutInfo.visibleItemsInfo.firstOrNull()?.offset?.y ?: 0
            )
        }

        onScrollPositionChanged()

        snapshotFlow { scrollState.layoutInfo.viewportStartOffset }
            .distinctUntilChanged()
            .collect {
                onScrollPositionChanged()
            }
    }

    LaunchedEffect(viewModel.currentScrollPosition) {
        lazyStaggeredGridState.scrollToItem(
            viewModel.currentScrollPosition,
            viewModel.currentScrollPositionOffset
        )
    }

    AnimatedContent(
        modifier = Modifier
            .fillMaxSize(),
        targetState = response,
        label = "animatedContent",
        transitionSpec = {
            fadeIn(
                animationSpec = tween(
                    300,
                    easing = LinearEasing
                )
            ) togetherWith fadeOut(
                animationSpec = tween(
                    300,
                    easing = LinearEasing
                )
            )
        }
    ) {
        when (it) {
            is Response.Error -> {
                val msg = it.error.localizedMessage ?: stringResource(R.string.error_loading)
                ErrorScreen(msg)
            }

            is Response.Idle -> {}

            is Response.Loading -> {
                LoadingScreen()
            }

            is Response.Success -> {
                val data = it.data
                HeroesList(
                    heroes = data,
                    listState = lazyStaggeredGridState,
                    onHeroClick = onHeroClick,
                )
            }
        }
    }
}