package com.exraion.beu.data.source.remote.api.model.auth

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @field:SerializedName("token")
    val token: String
)
