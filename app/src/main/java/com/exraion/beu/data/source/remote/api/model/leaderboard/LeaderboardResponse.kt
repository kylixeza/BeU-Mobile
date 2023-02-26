package com.exraion.model.leaderboard

import com.google.gson.annotations.SerializedName

data class LeaderboardResponse(
	
	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("avatar")
	val avatar: String,

	@field:SerializedName("xp")
	val xp: Int,

	@field:SerializedName("rank")
	val rank: Int
)
