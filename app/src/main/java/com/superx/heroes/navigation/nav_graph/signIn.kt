package com.superx.heroes.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.superx.heroes.feature.auth.presentation.LoginScreen
import com.superx.heroes.navigation.Screen

fun NavGraphBuilder.signIn(navController: NavController){
    composable(
        route = Screen.LoginScreen.route
    ) {
        LoginScreen(
            onSuccessLogin = {
                navController.navigate(Screen.HomeScreen.route)
            },
            onSignUpClicked = {
                navController.navigate(Screen.SignUpScreen.route)
            }
        )
    }
}