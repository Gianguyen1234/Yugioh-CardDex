package com.example.ygocarddex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ygocarddex.components.YugiohCard
import com.example.ygocarddex.data.models.Card
import com.example.ygocarddex.data.models.CardImage
import com.example.ygocarddex.data.models.CardPrice

@Composable
fun CardListScreen(
    navController: NavController,  // Add this parameter for NavController
    cards: List<Card>,
    onCardClick: (Card) -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit  // Add this parameter to handle search click
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) }

    // Filter cards based on search query
    val filteredCards = cards.filter { card ->
        card.name.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize().padding(0.dp)) {
        // Top App Bar with Menu icon and Search functionality
        TopAppBarComponent(
            title = "Yu-Gi-Oh! Card List",
            onMenuClick = onMenuClick,  // Menu icon action
            onSearchClick = onSearchClick  // Pass search click handler
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

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (searchQuery.isEmpty()) {
            // Placeholder text
            Text(
                text = "Search for a card...",
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            )
        }

        BasicTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Handle Done action */ }
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )
    }
}


@Preview
@Composable
fun CardListScreenPreview() {
    // Provide a mock NavController to avoid passing null
    val navController = rememberNavController()

    // Preview with sample data
    CardListScreen(
        navController = navController,
        cards = sampleCardList,
        onCardClick = {},
        onMenuClick = {},
        onSearchClick = { /* No action for preview */ }
    )
}
// Sample data for preview
val sampleCardList = listOf(
    Card(
        id = 1,
        name = "Blue-Eyes White Dragon",
        type = "Normal Monster",
        desc = "A legendary dragon that is a powerful engine of destruction.",
        race = "Dragon",
        archetype = "Blue-Eyes",
        card_images = listOf(CardImage(image_url = "https://ygoprodeck.com/card/a-cell-breeding-device-9766")),
        card_prices = listOf(
            CardPrice(
                cardmarket_price = "20.00",
                tcgplayer_price = "18.50",
                ebay_price = "22.00",
                amazon_price = "23.00",
                coolstuffinc_price = "21.00"
            )
        )
    ),
    Card(
        id = 2,
        name = "Dark Magician",
        type = "Spellcaster",
        desc = "The ultimate wizard in terms of attack and defense.",
        race = "Spellcaster",
        archetype = "Dark Magician",
        card_images = listOf(CardImage(image_url = "https://ygoprodeck.com/card/a-cell-breeding-device-9766")),
        card_prices = listOf(
            CardPrice(
                cardmarket_price = "15.00",
                tcgplayer_price = "14.00",
                ebay_price = "16.50",
                amazon_price = "17.00",
                coolstuffinc_price = "16.00"
            )
        )
    )
)

