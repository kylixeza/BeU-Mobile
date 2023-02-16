package com.exraion.beu.data.source.remote.api.model.auth

import com.google.gson.annotations.SerializedName

data class RegisterBody(
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("password")
    val password: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("phone_number")
    val phoneNumber: String,
    @field:SerializedName("location")
    val location: String
)
