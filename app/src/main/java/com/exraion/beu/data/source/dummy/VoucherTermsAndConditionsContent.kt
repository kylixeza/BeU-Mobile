package com.exraion.beu.data.source.dummy

import com.exraion.beu.model.VoucherDetail

fun VoucherDetail.getProductVoucherTermsAndConditions() = listOf(
    "Get $discount% discount on the price of groceries",
    "Minimum purchase of Rp$minimumSpend",
    "Discount vouchers of up to $discount% are worth up to a maximum of Rp$maximumDiscount",
    "Valid for all BeU cooking tutorials",
    "The voucher will only be valid for one use",
    "Users are expected to pay attention to the expiration date of the voucher to be redeemed",
)

fun VoucherDetail.getShippingVoucherTermsAndConditions() = listOf(
    if(discount == 100) "Get free shipping" else "Get $discount% discount on shipping costs",
    if(minimumSpend == 0) "No minimum purchase needed" else "Minimum purchase of Rp$minimumSpend",
    "Valid for all BeU cooking tutorials",
    "The voucher will only be valid for one use",
    "Users are expected to pay attention to the expiration date of the voucher to be redeemed",
)