package com.exraion.beu.data.source.remote.api.model.ingredient

import com.google.gson.annotations.SerializedName

data class IngredientResponse(
    @field:SerializedName("ingredient")
    val ingredient: String,
    
    @field:SerializedName("price")
    val price: Int,
)
