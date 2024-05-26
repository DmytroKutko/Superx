package com.superx.heroes.feature.home.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.superx.heroes.feature.home.presentation.components.HeroRowCard
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun GroupsScreen(
    onHeroClick: (Int) -> Unit,
    viewModel: GroupsViewModel = hiltViewModel(),
) {
    val lazyListState = rememberLazyListState()

    val criminalsList by viewModel.criminalsList.collectAsStateWithLifecycle()
    val assassinsList by viewModel.assassinsList.collectAsStateWithLifecycle()
    val mercenaryList by viewModel.mercenaryList.collectAsStateWithLifecycle()
    val soldiersList by viewModel.soldiersList.collectAsStateWithLifecycle()

    LaunchedEffect(lazyListState) {
        val onScrollPositionChanged: () -> Unit = {
            viewModel.currentScrollPosition =
                lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
            viewModel.currentScrollPositionOffset = Math.abs(
                lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()?.offset ?: 0
            )
        }

        onScrollPositionChanged()

        snapshotFlow { lazyListState.layoutInfo.viewportStartOffset }
            .distinctUntilChanged()
            .collect {
                onScrollPositionChanged()
            }
    }

    LaunchedEffect(viewModel.currentScrollPosition) {
        lazyListState.scrollToItem(
            viewModel.currentScrollPosition,
            viewModel.currentScrollPositionOffset
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyListState
    ) {
        // Criminals
        item {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp, start = 8.dp),
                text = "Criminals",
                fontSize = 24.sp
            )
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(criminalsList) {
                    HeroRowCard(hero = it, onHeroClick = onHeroClick)
                }
            }
        }

        // Assassins
        item {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp, start = 8.dp),
                text = "Assassins",
                fontSize = 24.sp
            )
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(assassinsList) {
                    HeroRowCard(hero = it, onHeroClick = onHeroClick)
                }
            }
        }

        // Mercenary
        item {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp, start = 8.dp),
                text = "Mercenary",
                fontSize = 24.sp
            )
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(mercenaryList) {
                    HeroRowCard(hero = it, onHeroClick = onHeroClick)
                }
            }
        }

        // Soldiers
        item {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp, start = 8.dp),
                text = "Soldiers",
                fontSize = 24.sp
            )
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(soldiersList) {
                    HeroRowCard(hero = it, onHeroClick = onHeroClick)
                }
            }
        }

    }
}