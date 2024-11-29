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
import androidx.compose.ui.draw.clip
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
            // Card Image with Vignette Effect
            val imageUrl = card.card_images.firstOrNull()?.image_url_cropped ?: card.card_images.firstOrNull()?.image_url

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = card.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Card Name
            Text(
                text = card.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // New Box with TypeRaceArchetypeSection on the left and AtkDefAttributeSection on the right
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Type, Race, Archetype Section
                    Column(modifier = Modifier.weight(1f)) {
                        TypeRaceArchetypeSection(card = card)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // ATK, DEF, Attribute Section
                    Column(modifier = Modifier.weight(1f)) {
                        AtkDefAttributeSection(card = card)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Card Description
            Text(
                text = truncateDescription(card.desc, 20),
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
                "Flip Effect Monster" -> Color(0xFFFFD700) // Golden yellow for Flip Effect Monsters
                "Union Effect Monster" -> Color(0xFF6A5ACD) // Slate blue for Union Effect Monsters
                "Link Monster" -> Color(0xFF6495ED) // Cornflower blue for Link Monsters
                "Effect Monster" -> Color(0xFF8FBC8F) // Dark sea green for Effect Monsters
                "Fusion Monster" -> Color(0xFFEE82EE) // Violet for Fusion Monsters
                "Pendulum Effect Monster" -> Color(0xFFFF6347) // Tomato red for Pendulum Effect Monsters
                "XYZ Monster" -> Color(0xFF4682B4) // Steel blue for XYZ Monsters
                "Synchro Monster" -> Color(0xFFF0A7ED) // Light Steel Blue for Synchro Monsters
                "Synchro Tuner Monster" -> Color(0xFFDA70D6) // Orchid color for Synchro Tuner Monsters
                "Tuner Monster" -> Color(0xFF125205)
                else -> MaterialTheme.colorScheme.surfaceVariant
            },
            textColor = when (card.type) {
                "Monster Card" -> MaterialTheme.colorScheme.onErrorContainer
                "Spell Card" -> MaterialTheme.colorScheme.onTertiaryContainer
                "Trap Card" -> MaterialTheme.colorScheme.onSecondaryContainer
                "Flip Effect Monster" -> Color(0xFF000000) // Black text for contrast on golden yellow
                "Union Effect Monster" -> Color(0xFFFFFFFF) // White text for contrast on slate blue
                "Link Monster" -> Color(0xFFFFFFFF) // White text for contrast on cornflower blue
                "Effect Monster" -> Color(0xFF000000) // Black text for contrast on dark sea green
                "Fusion Monster" -> Color(0xFF000000) // Black text for contrast on violet
                "Pendulum Effect Monster" -> Color(0xFFFFFFFF) // White text for contrast on tomato red
                "XYZ Monster" -> Color(0xFFFFFFFF) // White text for contrast on steel blue
                "Synchro Monster" -> Color(0xFF000000) // Black text for contrast on light steel blue
                "Synchro Tuner Monster" -> Color(0xFFFFFFFF) // White text for contrast on orchid color
                "Tuner Monster" -> Color(0xFFFFFFFF)
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
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}

@Composable
fun AtkDefAttributeSection(card: Card) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Display ATK with a bold red background
        card.atk?.let {
            BadgeRow(
                label = "ATK",
                value = it.toString(),
                backgroundColor = Color(0xFFE57373), // Light red for ATK
                textColor = Color(0xFFB71C1C) // Dark red for text
            )
        }

        // Display DEF with a bold blue background
        card.def?.let {
            BadgeRow(
                label = "DEF",
                value = it.toString(),
                backgroundColor = Color(0xFF64B5F6), // Light blue for DEF
                textColor = Color(0xFF0D47A1) // Dark blue for text
            )
        }

        // Display Attribute with a color depending on the attribute type
        card.attribute?.let { attribute ->
            val (backgroundColor, textColor) = getAttributeColors(attribute)
            BadgeRow(
                label = "Attribute",
                value = attribute,
                backgroundColor = backgroundColor,
                textColor = textColor
            )
        }
    }
}

/**
 * Returns a pair of background and text colors for each attribute type.
 */
fun getAttributeColors(attribute: String): Pair<Color, Color> {
    return when (attribute) {
        "DARK" -> Pair(Color(0xFF424242), Color(0xFFFFFFFF)) // Dark gray background, white text
        "LIGHT" -> Pair(Color(0xFFFFF9C4), Color(0xFFFFC107)) // Light yellow background, golden text
        "FIRE" -> Pair(Color(0xFFFF7043), Color(0xFFD84315)) // Orange background, dark orange text
        "WATER" -> Pair(Color(0xFF64B5F6), Color(0xFF1565C0)) // Light blue background, navy text
        "EARTH" -> Pair(Color(0xFF8D6E63), Color(0xFF3E2723)) // Brown background, dark brown text
        "WIND" -> Pair(Color(0xFF81C784), Color(0xFF2E7D32)) // Light green background, forest green text
        "DIVINE" -> Pair(Color(0xFFE6EE9C), Color(0xFFAFB42B)) // Pale gold background, olive green text
        else -> Pair(Color(0xFFE0E0E0), Color(0xFF616161)) // Default: Light gray background, dark gray text
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

/**
 * Truncate a string to a specified number of words, adding "..." if truncated.
 */
fun truncateDescription(description: String, maxWords: Int): String {
    val words = description.split(" ")
    return if (words.size > maxWords) {
        words.take(maxWords).joinToString(" ") + "..."
    } else {
        description
    }
}
