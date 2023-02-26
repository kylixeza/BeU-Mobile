package com.exraion.model.history

import com.google.gson.annotations.SerializedName

data class HistoryUpdateStarsGiven(
	@field:SerializedName("stars_given")
	val starsGiven: Double
)
