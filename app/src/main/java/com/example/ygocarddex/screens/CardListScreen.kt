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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ygocarddex.components.YugiohCard
import com.example.ygocarddex.data.models.Card
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen(
    navController: NavController,  // Navigation Controller
    cards: List<Card>,
    onCardClick: (Card) -> Unit,
    onSearchClick: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // State to manage drawer
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = { SidebarContent( navController= navController) }, // Separate composable for drawer content
        drawerState = drawerState
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
                onMenuClick = {
                    // Open or close the drawer when menu is clicked
                    scope.launch {
                        if (drawerState.isClosed) drawerState.open()
                        else drawerState.close()
                    }
                },
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

//
//@Preview
//@Composable
//fun CardListScreenPreview() {
//    // Provide a mock NavController to avoid passing null
//    val navController = rememberNavController()
//
//    // DrawerState for handling sidebar functionality
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//    // Wrap with ModalNavigationDrawer for previewing the sidebar
//    ModalNavigationDrawer(
//        drawerContent = { SidebarContent() },
//        drawerState = drawerState
//    ) {
//        // Preview with sample data
//        CardListScreen(
//            navController = navController,
//            cards = sampleCardList,
//            onCardClick = { /* No action for preview */ },
//            onMenuClick = {
//                // Simulate opening/closing the drawer
//                scope.launch {
//                    if (drawerState.isClosed) drawerState.open()
//                    else drawerState.close()
//                }
//            },
//            onSearchClick = { /* No action for preview */ }
//        )
//    }
//}


// Sample data for preview
//val sampleCardList = listOf(
//    Card(
//        id = 1,
//        name = "Blue-Eyes White Dragon",
//        type = "Normal Monster",
//        desc = "A legendary dragon that is a powerful engine of destruction.",
//        race = "Dragon",
//        archetype = "Blue-Eyes",
//        card_images = listOf(CardImage(image_url = "https://ygoprodeck.com/card/a-cell-breeding-device-9766")),
//        card_prices = listOf(
//            CardPrice(
//                cardmarket_price = "20.00",
//                tcgplayer_price = "18.50",
//                ebay_price = "22.00",
//                amazon_price = "23.00",
//                coolstuffinc_price = "21.00"
//            )
//        )
//    ),
//    Card(
//        id = 2,
//        name = "Dark Magician",
//        type = "Spellcaster",
//        desc = "The ultimate wizard in terms of attack and defense.",
//        race = "Spellcaster",
//        archetype = "Dark Magician",
//        card_images = listOf(CardImage(image_url = "https://ygoprodeck.com/card/a-cell-breeding-device-9766")),
//        card_prices = listOf(
//            CardPrice(
//                cardmarket_price = "15.00",
//                tcgplayer_price = "14.00",
//                ebay_price = "16.50",
//                amazon_price = "17.00",
//                coolstuffinc_price = "16.00"
//            )
//        )
//    )
//)

