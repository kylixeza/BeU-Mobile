package com.exraion.beu.model

data class VoucherAvailable(
    val shipping: List<VoucherList>,
    val product: List<VoucherList>,
)

data class VoucherList(
    val voucherId: String,
    val category: String,
    val xpCost: Int,
    val validUntil: String,
    val discount: Int,
    val minimumSpend: Int,
    val maximumDiscount: Int,
)

data class VoucherDetail(
    val voucherId: String,
    val category: String,
    val xpCost: Int,
    val validUntil: String,
    val discount: Int,
    val minimumSpend: Int,
    val maximumDiscount: Int,
)
