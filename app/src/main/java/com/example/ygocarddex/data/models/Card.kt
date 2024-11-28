package com.example.ygocarddex.data.models

data class CardResponse(val data: List<Card>)
data class Card(
    val id: Int,
    val name: String,
    val type: String,
    val desc: String,
    val race: String,
    val archetype: String?,
    val card_images: List<CardImage>,
    val card_prices: List<CardPrice>
)