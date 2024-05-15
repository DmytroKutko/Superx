package com.superx.heroes.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.superx.heroes.feature.favorites.presentation.FavoriteScreen
import com.superx.heroes.navigation.Screen
import com.superx.heroes.navigation.Tab

fun NavGraphBuilder.favorites(navController: NavController) {
    navigation(
        startDestination = Screen.FavoritesScreen.route,
        route = Tab.Favorites.route
    ) {
        composable(
            route = Screen.FavoritesScreen.route,
        ) {
            FavoriteScreen(
                onHeroClick = { heroId ->
                    navController.navigate(route = "${Screen.ProfileScreen.route}/$heroId")
                }
            )
        }
    }
}