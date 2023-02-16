package com.exraion.beu.data.source.remote.api.model.user

import com.google.gson.annotations.SerializedName

data class UserBody(
	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("address")
	val location: String,

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
