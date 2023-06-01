package com.exraion.beu.data.source.remote.api.model.voucher

import com.google.gson.annotations.SerializedName

data class VoucherSecretResponse(
    @field:SerializedName("is_success_redeemed")
    val isSuccessRedeemed: Boolean,
    @field:SerializedName("message")
    val message: String,
)
