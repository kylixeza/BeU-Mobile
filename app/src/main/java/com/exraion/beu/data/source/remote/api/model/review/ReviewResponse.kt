package com.exraion.model.review

import com.google.gson.annotations.SerializedName

data class ReviewResponse(

	@field:SerializedName("name")
	val name: String,
	
	@field:SerializedName("avatar")
	val avatar: String,
	
	@field:SerializedName("rating")
	val rating: Double
)
