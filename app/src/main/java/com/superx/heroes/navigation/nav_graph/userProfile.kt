package com.superx.heroes.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.superx.heroes.feature.user.presentation.UserProfileScreen
import com.superx.heroes.navigation.Screen
import com.superx.heroes.navigation.Tab

fun NavGraphBuilder.userProfile(navController: NavController) {
    navigation(
        startDestination = Screen.UserProfileScreen.route,
        route = Tab.UserProfile.route
    ) {
        composable(
            route = Screen.UserProfileScreen.route,
        ) {
            UserProfileScreen(
                onEditClicked = {

                },
                onSignOutClicked = {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}