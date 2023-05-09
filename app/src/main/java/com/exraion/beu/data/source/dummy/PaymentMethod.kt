package com.exraion.beu.data.source.dummy

import com.exraion.beu.R

fun getPaymentMethods() = listOf(
    PaymentMethod(1, "BeU Pay", R.drawable.ic_beu_pay),
    PaymentMethod(2, "BNI", R.drawable.ic_bni),
    PaymentMethod(3, "Bank Jago", R.drawable.ic_jago),
    PaymentMethod(4, "BSI", R.drawable.ic_bsi),
    PaymentMethod(5, "BCA", R.drawable.ic_bca),
    PaymentMethod(6, "Bank Mandiri", R.drawable.ic_mandiri),
    PaymentMethod(7, "Bank Mega", R.drawable.ic_mega),
    PaymentMethod(8, "BRI", R.drawable.ic_bri),
    PaymentMethod(9, "BTN", R.drawable.ic_btn),
)

data class PaymentMethod(
    val id: Int,
    val name: String,
    val image: Int,
)