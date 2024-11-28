package com.example.ygocarddex.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.ygocarddex.data.models.Card

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailScreen(card: Card, onBackClick: () -> Unit) {
    val scrollState = rememberScrollState()
    var currentImageIndex by remember { mutableIntStateOf(0) } // Track swiped image index
    var isExpanded by remember { mutableStateOf(false) }       // Track fullscreen state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Top Bar
        CardDetailTopBar(title = card.name, onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(16.dp))

        // Expandable and Swipeable Image Section
        SwipeableExpandableImage(
            imageUrls = card.card_images.map { it.image_url },
            currentImageIndex = currentImageIndex,
            onImageChange = { currentImageIndex = it },
            isExpanded = isExpanded,
            onExpandToggle = { isExpanded = !isExpanded },
            scrollState = scrollState
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card Details Section
        CardDetailsSection(card = card)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailTopBar(title: String, onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun SwipeableExpandableImage(
    imageUrls: List<String>,
    currentImageIndex: Int,
    onImageChange: (Int) -> Unit,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    scrollState: androidx.compose.foundation.ScrollState
) {
    val maxIndex = imageUrls.size - 1
    val scale = 1f - (scrollState.value / 1000f).coerceIn(0f, 0.3f) // Add a subtle scale effect

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume() // Consume touch events
                    if (dragAmount > 50 && currentImageIndex > 0) {
                        onImageChange(currentImageIndex - 1) // Swipe left
                    } else if (dragAmount < -50 && currentImageIndex < maxIndex) {
                        onImageChange(currentImageIndex + 1) // Swipe right
                    }
                }
            }
            .graphicsLayer(scaleX = scale, scaleY = scale) // Apply scale effect
            .clip(RoundedCornerShape(16.dp))
            .shadow(12.dp, RoundedCornerShape(16.dp))
    ) {
        // Display Current Image
        Image(
            painter = rememberAsyncImagePainter(model = imageUrls[currentImageIndex]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clickable { onExpandToggle() } // Toggle fullscreen dialog
        )

        // Fullscreen Dialog
        if (isExpanded) {
            Dialog(onDismissRequest = { onExpandToggle() }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrls[currentImageIndex]),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(
                        onClick = { onExpandToggle() },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Fullscreen",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardDetailsSection(card: Card) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Name: ${card.name}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Type: ${card.type}",
            style = MaterialTheme.typography.bodyLarge,
            color = when (card.type) {
                "Monster Card" -> MaterialTheme.colorScheme.error
                "Spell Card" -> MaterialTheme.colorScheme.tertiary
                "Trap Card" -> MaterialTheme.colorScheme.secondary
                else -> MaterialTheme.colorScheme.onSurface
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = card.desc,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (card.card_prices.isNotEmpty()) {
            Text(
                text = "Prices:",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(bottom = 32.dp)) {
                card.card_prices.forEach { price ->
                    PriceRow(label = "CardMarket", price = price.cardmarket_price)
                    PriceRow(label = "TCGPlayer", price = price.tcgplayer_price)
                    PriceRow(label = "eBay", price = price.ebay_price)
                    PriceRow(label = "Amazon", price = price.amazon_price)
                    PriceRow(label = "CoolStuffInc", price = price.coolstuffinc_price)
                }
            }
        }
    }
}

@Composable
fun PriceRow(label: String, price: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.ShoppingCart,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$label Price:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            text = "$$price",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
