package com.exraion.beu.data.source.remote.api.model.favorite

import com.google.gson.annotations.SerializedName

data class FavoriteBody(
    @field:SerializedName("menu_id")
    val menuId: String
)
