package com.exraion.beu.data.source.remote.api.model.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
	
	@field:SerializedName("location")
	val location: String,
	
	@field:SerializedName("avatar")
	val avatar: String,
	
	@field:SerializedName("beu_pay")
	val beUPay: Int,
	
	@field:SerializedName("email")
	val email: String,
	
	@field:SerializedName("name")
	val name: String,
	
	@field:SerializedName("phone_number")
	val phoneNumber: String,
	
	@field:SerializedName("xp")
	val xp: Int
)
