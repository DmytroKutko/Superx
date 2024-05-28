package com.superx.heroes.navigation.nav_graph

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.superx.heroes.feature.profile.presentation.ProfileScreen
import com.superx.heroes.navigation.Screen

fun NavGraphBuilder.heroProfile(navController: NavController) {
    composable(
        route = "${Screen.ProfileScreen.route}/{heroId}",
        arguments = listOf(navArgument("heroId") { type = NavType.IntType }),
        enterTransition = {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        },
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        }
    ) { entry ->
        entry.arguments?.getInt("heroId")?.let {
            ProfileScreen(it, navController)
        }
    }

}