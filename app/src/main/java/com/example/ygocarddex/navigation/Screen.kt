package com.example.ygocarddex.navigation

sealed class Screen(val route: String) {
    object CardList : Screen("cardList")
    object CardDetail : Screen("cardDetail/{cardId}") {
        fun createRoute(cardId: String) = "cardDetail/$cardId"
    }
    object SearchScreen : Screen("searchScreen")
}
