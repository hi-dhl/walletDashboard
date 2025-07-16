package com.dhl.wallet.dashboard.model

data class Rate(
    val amount: String,
    val rate: String
)

data class LiveRate(
    val from_currency: String,
    val to_currency: String,
    val rates: List<Rate>,
    val time_stamp: Long
)

data class LiveRateResponse(
    val ok: Boolean,
    val warning: String,
    val tiers: List<LiveRate>
)