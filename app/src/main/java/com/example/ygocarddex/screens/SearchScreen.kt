package com.example.ygocarddex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.ygocarddex.components.YugiohCard
import com.example.ygocarddex.data.models.Card
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    cards: List<Card>, // List of cards passed to the screen
    onCardClick: (Card) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // State to manage drawer
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = { SidebarContent(navController = navController) }, // Drawer content
        drawerState = drawerState
    ) {
        var searchQuery by remember { mutableStateOf("") }
        var isSearchVisible by remember { mutableStateOf(true) }

        // Filter cards based on search query
        val filteredCards = cards.filter { card ->
            card.name.contains(searchQuery, ignoreCase = true) // Filter cards by name
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // Top App Bar with Search functionality and Menu
            TopAppBarComponent(
                title = "Search Yu-Gi-Oh! Cards",
                onMenuClick = {
                    scope.launch {
                        if (drawerState.isClosed) drawerState.open()  // Open the drawer
                        else drawerState.close()                     // Close the drawer
                    }
                },
                onSearchClick = { isSearchVisible = !isSearchVisible } // Toggle visibility of search bar
            )

            // Show the search bar when isSearchVisible is true
            if (isSearchVisible) {
                SearchBar(searchQuery = searchQuery, onSearchQueryChange = { searchQuery = it })
            }

            // List of filtered cards
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredCards) { card ->
                    YugiohCard(card = card, onCardClick = onCardClick)
                }
            }
        }
    }
}



//@Preview
//@Composable
//fun SearchScreenPreview() {
//    SearchScreen(onBackClick = {})
//}