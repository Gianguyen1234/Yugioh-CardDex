package com.example.ygocarddex.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ygocarddex.data.models.Card

@Composable
fun YugiohCard(
    card: Card,
    onCardClick: (Card) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onCardClick(card) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Card Image
            Image(
                painter = rememberAsyncImagePainter(model = card.card_images.first().image_url),
                contentDescription = card.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Card Name
            Text(
                text = card.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Distinct Section for Type, Race, and Archetype
            TypeRaceArchetypeSection(card = card)

            // Card Description
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = card.desc,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun TypeRaceArchetypeSection(card: Card) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Card Type
        BadgeRow(
            label = "Type",
            value = card.type,
            backgroundColor = when (card.type) {
                "Monster Card" -> MaterialTheme.colorScheme.errorContainer
                "Spell Card" -> MaterialTheme.colorScheme.tertiaryContainer
                "Trap Card" -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.surfaceVariant
            },
            textColor = when (card.type) {
                "Monster Card" -> MaterialTheme.colorScheme.onErrorContainer
                "Spell Card" -> MaterialTheme.colorScheme.onTertiaryContainer
                "Trap Card" -> MaterialTheme.colorScheme.onSecondaryContainer
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            }
        )

        // Card Race
        BadgeRow(
            label = "Race",
            value = card.race,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            textColor = MaterialTheme.colorScheme.onPrimaryContainer
        )

        // Card Archetype
        card.archetype?.let {
            BadgeRow(
                label = "Archetype",
                value = it,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun BadgeRow(label: String, value: String, backgroundColor: Color, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(end = 8.dp)
        )
        Box(
            modifier = Modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



