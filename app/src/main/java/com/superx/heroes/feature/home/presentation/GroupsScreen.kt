package com.superx.heroes.feature.home.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GroupsScreen(
    onHeroClick: (Int) -> Unit,
    groupsViewModel: GroupsViewModel = hiltViewModel(),
) {

    LazyColumn {

    }
}