
package com.example.ygocarddex.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.ygocarddex.screens.CardDetailScreen
import com.example.ygocarddex.screens.CardListScreen
import com.example.ygocarddex.screens.SearchScreen
import com.example.ygocarddex.viewmodel.UiState
import com.example.ygocarddex.viewmodel.YugiohViewModel

@Composable
fun NavGraph(viewModel: YugiohViewModel = viewModel()) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = Screen.CardList.route) {
        composable(Screen.CardList.route) {
            when (uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                }
                is UiState.Success -> {
                    val state = uiState as UiState.Success
                    CardListScreen(
                        navController = navController,
                        cards = state.cards,
                        onCardClick = { selectedCard ->
                            navController.navigate(Screen.CardDetail.createRoute(selectedCard.id.toString()))
                        },
                        onSearchClick = {
                            navController.navigate(Screen.SearchScreen.route)
                        }
                    )
                }
                is UiState.Error -> {
                    val state = uiState as UiState.Error
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        composable(Screen.SearchScreen.route) {
            when (uiState) {
                is UiState.Success -> {
                    val state = uiState as UiState.Success
                    SearchScreen(
                        navController = navController,
                        cards = state.cards, // Pass the card list
                        onCardClick = { selectedCard ->
                            navController.navigate(Screen.CardDetail.createRoute(selectedCard.id.toString()))
                        }
                    )
                }
                else -> {
                    Text(
                        text = "Loading...",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        composable(
            route = Screen.CardDetail.route,
            arguments = listOf(navArgument("cardId") { type = NavType.StringType })
        ) { backStackEntry ->
            val cardId = backStackEntry.arguments?.getString("cardId")?.toIntOrNull()
            val selectedCard = (uiState as? UiState.Success)?.cards?.find { it.id == cardId }

            if (selectedCard != null) {
                CardDetailScreen(
                    card = selectedCard,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                Text(
                    text = "Card not found",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

