package com.exraion.beu.data.source.remote.api.model.dailyxp

import com.google.gson.annotations.SerializedName

data class DailyXpRequest(
    @field:SerializedName("daily_xp_id")
    val dailyXpId: String,
)
