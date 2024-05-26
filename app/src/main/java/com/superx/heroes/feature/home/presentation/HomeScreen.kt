package com.superx.heroes.feature.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onHeroClick: (Int) -> Unit,
) {
    val titles = listOf("Groups", "All Heroes")

    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .shadow(4.dp),
                title = { Text(text = "Home") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                divider = {},
            ) {
                Tab(selected = pagerState.currentPage == 0, text = {
                    Text(
                        text = titles[0],
                        fontFamily = ubuntuFontFamily,
                        color = MaterialTheme.colorScheme.primary
                    )
                }, onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                })
                Tab(selected = pagerState.currentPage == 1, text = {
                    Text(
                        text = titles[1],
                        fontFamily = ubuntuFontFamily,
                        color = MaterialTheme.colorScheme.primary
                    )
                }, onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                })
            }

            HorizontalPager(
                state = pagerState, userScrollEnabled = false
            ) { page ->
                if (page == 0) {
                    GroupsScreen(onHeroClick = onHeroClick)
                } else if (page == 1) {
                    HeroesScreen(onHeroClick = onHeroClick)
                }
            }
        }
    }
}