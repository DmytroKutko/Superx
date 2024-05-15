package com.superx.heroes.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    data object HeroesScreen : Screen("heroes_screen")
    data object FavoritesScreen : Screen("favorites_screen")
    data object ProfileScreen : Screen("profile_screen")
}

sealed class Tab(
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String,
) {
    data object Heroes : Tab(
        route = "heroes_tab",
        icon = BottomAppBarIcons.heroIconOutlined,
        selectedIcon = BottomAppBarIcons.heroIconFilled,
        label = "Heroes"
    )

    data object Favorites : Tab(
        route = "favorites_tab",
        icon = BottomAppBarIcons.favoritesIconOutlined,
        selectedIcon = BottomAppBarIcons.favoritesIconFilled,
        label = "Favorites"
    )
}

private object BottomAppBarIcons {
    val heroIconOutlined = Icons.AutoMirrored.Outlined.List
    val heroIconFilled = Icons.AutoMirrored.Filled.List

    val favoritesIconOutlined = Icons.Outlined.StarOutline
    val favoritesIconFilled = Icons.Filled.Star
}

val bottomNavTabs = listOf(
    Tab.Heroes,
    Tab.Favorites
)