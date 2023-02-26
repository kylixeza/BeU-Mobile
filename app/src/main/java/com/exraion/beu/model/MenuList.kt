package com.exraion.beu.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class MenuList(
    val menuId: String,
    val image: String,
    val title: String,
    val rangePrice: String,
    val rating: BigDecimal,
    val cookTime: Int,
    val isFavorite: Boolean,
)
