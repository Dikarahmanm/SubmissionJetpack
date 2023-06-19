package com.dika.starrail.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Detail : Screen("home/{CharacterId}") {
        fun createRoute(CharacterId: Int) = "home/$CharacterId"
    }
}
