package com.dhl.wallet.dashboard.model

data class WalletBalance(
    val currency: String,
    val amount: Double
)

data class WalletBalanceResponse(
    val ok: Boolean,
    val warning: String,
    val wallet: List<WalletBalance>
)