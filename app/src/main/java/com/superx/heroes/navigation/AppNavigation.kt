package com.superx.heroes.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily
import com.superx.heroes.navigation.nav_graph.favorites
import com.superx.heroes.navigation.nav_graph.home
import com.superx.heroes.navigation.nav_graph.profile
import com.superx.heroes.navigation.nav_graph.signIn
import com.superx.heroes.navigation.nav_graph.signUp
import com.superx.heroes.navigation.nav_graph.userProfile


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val isBottomAppBarVisible = rememberSaveable(navBackStackEntry) {
        navBackStackEntry?.destination?.route == Screen.HomeScreen.route ||
                navBackStackEntry?.destination?.route == Screen.FavoritesScreen.route ||
                navBackStackEntry?.destination?.route == Screen.UserProfileScreen.route
    }

    Scaffold(
        bottomBar = {
            if (isBottomAppBarVisible) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            navController = navController,
            startDestination = Screen.LoginScreen.route
        ) {
            home(navController)
            favorites(navController)
            profile(navController)
            signIn(navController)
            signUp(navController)
            userProfile(navController)
        }
    }

}

@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavTabs.forEach { tab ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == tab.route
            } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = tab.label,
                        fontFamily = ubuntuFontFamily,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}