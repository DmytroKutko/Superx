package com.superx.heroes.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.superx.heroes.feature.heroes.presentation.HeroesScreen
import com.superx.heroes.navigation.Screen
import com.superx.heroes.navigation.Tab

fun NavGraphBuilder.heroes(navController: NavController) {
    navigation(
        startDestination = Screen.HeroesScreen.route,
        route = Tab.Heroes.route
    ) {
        composable(
            route = Screen.HeroesScreen.route
        ) {
            HeroesScreen(onHeroClick = { heroId ->
                navController.navigate(
                    route = "${Screen.ProfileScreen.route}/$heroId"
                )
            })
        }
    }
}