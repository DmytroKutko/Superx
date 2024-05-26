package com.superx.heroes.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.superx.heroes.feature.home.presentation.HeroesScreen
import com.superx.heroes.feature.home.presentation.HomeScreen
import com.superx.heroes.navigation.Screen
import com.superx.heroes.navigation.Tab

fun NavGraphBuilder.home(navController: NavController) {
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = Tab.Home.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(onHeroClick = { heroId ->
                navController.navigate(
                    route = "${Screen.ProfileScreen.route}/$heroId"
                )
            })
        }
    }
}