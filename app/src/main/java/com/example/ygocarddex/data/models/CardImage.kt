package com.example.ygocarddex.data.models

data class CardImage(
    val image_url: String,
    val image_url_small: String? = null, // Optional, since it may not always be used
    val image_url_cropped: String? = null // Optional, for cropped images
)
