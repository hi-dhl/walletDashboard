package com.dhl.wallet.dashboard.model

/**
 * <pre>
 *     author: dhl
 *     date  : 2025/7/16
 *     desc  :
 * </pre>
 */

// 支持的货币
data class Currency(
    val symbol: String,
    val name: String,
    val icon: String,
    val isDefault: Boolean = false
)

// 汇率
data class ExchangeRate(
    val symbol: String,
    val rate: Double,
    val timestamp: Long
)

// 钱包余额
data class WalletBalance(
    val symbol: String,
    val balance: Double
)

// UI展示模型
data class WalletItem(
    val currency: Currency,
    val balance: Double,
    val usdValue: Double,
    val rate: Double
)