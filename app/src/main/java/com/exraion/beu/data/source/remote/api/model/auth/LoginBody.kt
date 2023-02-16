package com.exraion.beu.data.source.remote.api.model.auth

import com.google.gson.annotations.SerializedName

data class LoginBody(
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("password")
    val password: String
)
