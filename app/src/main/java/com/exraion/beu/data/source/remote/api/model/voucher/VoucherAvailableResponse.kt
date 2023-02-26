package com.exraion.beu.data.source.remote.api.model.voucher

import com.google.gson.annotations.SerializedName

data class VoucherAvailableResponse(
	@field:SerializedName("shipping")
	val shipping: List<VoucherListResponse>,
	
	@field:SerializedName("product")
	val product: List<VoucherListResponse>,
)
