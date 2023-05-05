package com.exraion.beu.model

data class History(
    val orderId: String,
    val menuId: String,
    val title: String,
    val image: String,
    val timeStamp: String,
    val ingredients: List<String>,
    val status: String,
    val starsGiven: Double,
)
