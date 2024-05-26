package com.superx.heroes.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object UserProfileScreen : Screen("user_screen")
    data object FavoritesScreen : Screen("favorites_screen")
    data object ProfileScreen : Screen("profile_screen")
    data object LoginScreen : Screen("login_screen")
    data object SignUpScreen : Screen("sign_up_screen")
}

sealed class Tab(
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String,
) {
    data object Home : Tab(
        route = "home_tab",
        icon = BottomAppBarIcons.heroIconOutlined,
        selectedIcon = BottomAppBarIcons.heroIconFilled,
        label = "Home"
    )

    data object Favorites : Tab(
        route = "favorites_tab",
        icon = BottomAppBarIcons.favoritesIconOutlined,
        selectedIcon = BottomAppBarIcons.favoritesIconFilled,
        label = "Favorites"
    )

    data object UserProfile : Tab(
        route = "user_tab",
        icon = BottomAppBarIcons.userIconOutlined,
        selectedIcon = BottomAppBarIcons.userIconFilled,
        label = "Profile"
    )
}

private object BottomAppBarIcons {
    val heroIconOutlined = Icons.Outlined.Home
    val heroIconFilled = Icons.Filled.Home

    val favoritesIconOutlined = Icons.Outlined.StarOutline
    val favoritesIconFilled = Icons.Filled.Star

    val userIconOutlined = Icons.Outlined.PersonOutline
    val userIconFilled = Icons.Filled.Person
}

val bottomNavTabs = listOf(
    Tab.Home,
    Tab.Favorites,
    Tab.UserProfile
)