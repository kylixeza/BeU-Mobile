package com.exraion.model.favorite

import com.google.gson.annotations.SerializedName

data class FavoriteBody(
    @field:SerializedName("menu_id")
    val menuId: String
)
