package com.superx.heroes.feature.heroes.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.superx.heroes.R
import com.superx.heroes.feature.core.presentation.ErrorScreen
import com.superx.heroes.feature.core.presentation.LoadingScreen
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily
import com.superx.heroes.feature.heroes.presentation.components.HeroesList
import com.superx.heroes.util.Response
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroesScreen(
    onHeroClick: (Int) -> Unit,
    viewModel: HeroesViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val response by viewModel.response.collectAsStateWithLifecycle()

    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    LaunchedEffect(lazyStaggeredGridState) {
        val scrollState = lazyStaggeredGridState
        val onScrollPositionChanged: () -> Unit = {
            viewModel.currentScrollPosition = lazyStaggeredGridState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
            viewModel.currentScrollPositionOffset = Math.abs(lazyStaggeredGridState.layoutInfo.visibleItemsInfo.firstOrNull()?.offset?.y ?: 0)
        }

        onScrollPositionChanged() // Initial call to update scroll position

        // Observe changes in scroll position
        snapshotFlow { scrollState.layoutInfo.viewportStartOffset }
            .distinctUntilChanged()
            .collect {
                onScrollPositionChanged()
            }
    }

    LaunchedEffect(viewModel.currentScrollPosition) {
        lazyStaggeredGridState.scrollToItem(viewModel.currentScrollPosition, viewModel.currentScrollPositionOffset)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .shadow(elevation = 8.dp),
                title = {
                    Text(
                        text = stringResource(R.string.heroes),
                        fontFamily = ubuntuFontFamily,
                    )
                })
        }
    ) { paddingValues ->
        AnimatedContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
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
                        onHeroClick =  onHeroClick,
                    )
                }
            }
        }
    }
}