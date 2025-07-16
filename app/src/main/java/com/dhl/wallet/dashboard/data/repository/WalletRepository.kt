package com.dhl.wallet.dashboard.data.repository

import com.dhl.wallet.dashboard.model.Currency
import com.dhl.wallet.dashboard.model.LiveRate
import com.dhl.wallet.dashboard.model.WalletBalance
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    fun getCurrencies(): Flow<List<Currency>>
    fun getLiveRates(): Flow<List<LiveRate>>
    fun getWalletBalances(): Flow<List<WalletBalance>>
}