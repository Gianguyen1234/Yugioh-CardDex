package com.example.ygocarddex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ygocarddex.components.YugiohCard
import com.example.ygocarddex.data.models.Card
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController

@Composable
fun SearchScreen(
    navController: NavController,
    cards: List<Card>, // List of cards passed to the screen
    onCardClick: (Card) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(true) }

    // Filter cards based on search query
    val filteredCards = cards.filter { card ->
        card.name.contains(searchQuery, ignoreCase = true) // Filter cards by name
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar with Search functionality
        TopAppBarComponent(
            title = "Search Yu-Gi-Oh! Cards",
            onMenuClick = { /* Implement menu click */ },
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


//@Preview
//@Composable
//fun SearchScreenPreview() {
//    SearchScreen(onBackClick = {})
//}