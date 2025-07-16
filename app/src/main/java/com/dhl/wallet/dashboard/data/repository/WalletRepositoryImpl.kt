package com.dhl.wallet.dashboard.data.repository

import com.dhl.wallet.dashboard.AppHelper
import com.dhl.wallet.dashboard.data.local.LocalDataSource
import com.dhl.wallet.dashboard.model.Currency
import com.dhl.wallet.dashboard.model.LiveRate
import com.dhl.wallet.dashboard.model.WalletBalance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WalletRepositoryImpl: WalletRepository {
    private val localDataSource: LocalDataSource by lazy(mode = LazyThreadSafetyMode.NONE){
        LocalDataSource(AppHelper.mContext)
    }

    override fun getCurrencies(): Flow<List<Currency>> {
        return localDataSource.getCurrencies().map { response ->
            response.currencies
        }
    }

    override fun getLiveRates(): Flow<List<LiveRate>> {
        return localDataSource.getLiveRates().map { response ->
            response.tiers
        }
    }

    override fun getWalletBalances(): Flow<List<WalletBalance>> {
        return localDataSource.getWalletBalances().map { response ->
            response.wallet
        }
    }
}