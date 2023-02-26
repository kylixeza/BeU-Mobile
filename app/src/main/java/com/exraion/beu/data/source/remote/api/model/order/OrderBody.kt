package com.exraion.model.order

import com.google.gson.annotations.SerializedName

data class OrderBody(
    @field:SerializedName("menu_id")
    val menuId: String,

    @field:SerializedName("ingredients")
    val ingredients: List<String>,

    @field:SerializedName("total_price")
    val totalPrice: Int,
)
