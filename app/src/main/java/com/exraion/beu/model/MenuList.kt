package com.exraion.beu.model

data class MenuList(
    val menuId: String,
    val image: String,
    val title: String,
    val difficulty: String,
    val rangePrice: String,
    val rating: Double,
    val cookTime: Int,
    val isFavorite: Boolean,
    val isExclusive: Boolean
)
