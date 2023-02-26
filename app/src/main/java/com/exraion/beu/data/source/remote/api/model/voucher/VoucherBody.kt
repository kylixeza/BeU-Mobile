package com.exraion.model.voucher

import com.google.gson.annotations.SerializedName

data class VoucherBody(
	@field:SerializedName("voucher_secret_key")
	val voucherSecretKey: String,

	@field:SerializedName("xp_cost")
	val xpCost: Int,

	@field:SerializedName("valid_until")
	val validUntil: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("discount")
	val discount: Int,

	@field:SerializedName("minimum_spend")
	val minimumSpend: Int,

	@field:SerializedName("maximum_discount")
	val maximumDiscount: Int,
)
