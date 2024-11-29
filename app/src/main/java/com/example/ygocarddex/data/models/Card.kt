package com.example.ygocarddex.data.models

data class CardResponse(val data: List<Card>)
data class Card(
    val id: Int,
    val name: String,
    val type: String,
    val desc: String,
    val race: String,
    val archetype: String?,
    val atk: Int?, // Attack points (nullable to handle cases where it's missing)
    val def: Int?, // Defense points (nullable to handle cases where it's missing)
    val level: Int?, // Card level (nullable to handle cases where it's missing)
    val attribute: String?, // Card attribute (nullable to handle cases where it's missing)
    val card_images: List<CardImage>,
    val card_prices: List<CardPrice>
)