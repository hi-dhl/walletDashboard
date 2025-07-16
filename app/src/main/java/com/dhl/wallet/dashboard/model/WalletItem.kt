package com.dhl.wallet.dashboard.model

data class WalletItem(
    val currency: Currency,
    val balance: Double,
    val usdValue: Double,
    val liveRate: LiveRate?
)