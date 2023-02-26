package com.exraion.model.menu

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class MenuListResponse(
    @field:SerializedName("menu_id")
    val menuId: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("range_price")
    val rangePrice: String,

    @field:SerializedName("rating")
    val rating: BigDecimal,

    @field:SerializedName("cook_time")
    val cookTime: Int,

    @field:SerializedName("is_favorite")
    val isFavorite: Boolean,
)
