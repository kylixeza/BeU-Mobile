package com.exraion.beu.model

data class MenuDetail(
    val menuId: String,
    val title: String,
    val videoUrl: String,
    val isFavorite: Boolean,
    val isAvailable: Boolean,
    val ingredients: List<String>,
    val tools: List<String>,
    val steps: List<String>,
    val description: String,
    val estimatedTime: String,
    val benefit: String,
    val reviewsCount: Int,
    val averageRating: Double,
    val reviews: List<Review>
)
