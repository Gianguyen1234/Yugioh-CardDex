package com.example.ygocarddex.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ygocarddex.data.models.Card
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailScreen(card: Card, onBackClick: () -> Unit) {
    // Remember scroll state
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Top Bar
        CardDetailTopBar(
            title = card.name,
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cropped Card Image
        CroppedCardImage(
            imageUrl = card.card_images.first().image_url,
            topPadding = 20.dp,
            bottomPadding = 50.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card Details Section
        CardDetailsSection(card)
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
fun CroppedCardImage(imageUrl: String, topPadding: Dp, bottomPadding: Dp) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp) // Total height
                .padding(top = topPadding, bottom = bottomPadding) // Crop by adjusting paddings
                .clip(RoundedCornerShape(16.dp)) // Clip image to card shape
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun CardDetailsSection(card: Card) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Display Card Name and Type
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

        // Display Card Description
        Text(
            text = card.desc,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display Card Prices
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
