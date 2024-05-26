package com.superx.heroes.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.superx.heroes.feature.auth.presentation.LoginScreen
import com.superx.heroes.feature.auth.presentation.SignUpScreen
import com.superx.heroes.navigation.Screen

fun NavGraphBuilder.signUp(navController: NavController){
    composable(
        route = Screen.SignUpScreen.route
    ) {
        SignUpScreen(
            onSignUp = {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(0) { inclusive = true }
                }
            },
            onLoginClicked = {
                navController.navigate(Screen.LoginScreen.route)
            }
        )
    }
}