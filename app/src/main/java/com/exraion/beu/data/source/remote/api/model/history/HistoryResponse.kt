package com.exraion.model.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @field:SerializedName("order_id")
    val orderId: String,

    @field:SerializedName("menu_id")
    val menuId: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("time_stamp")
    val timeStamp: String,

    @field:SerializedName("ingredients")
    val ingredients: List<String>,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("stars_given")
    val starsGiven: Double,
)
