package com.dhl.wallet.dashboard.data.local

import android.content.Context
import com.dhl.wallet.dashboard.model.CurrencyResponse
import com.dhl.wallet.dashboard.model.LiveRateResponse
import com.dhl.wallet.dashboard.model.WalletBalanceResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.InputStreamReader

class LocalDataSource(private val context: Context) {
    private val gson = Gson()

    fun getCurrencies(): Flow<CurrencyResponse> = flow {
        val inputStream = context.assets.open("currencies.json")
        val reader = InputStreamReader(inputStream)
        val response = gson.fromJson(reader, CurrencyResponse::class.java)
        reader.close()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getLiveRates(): Flow<LiveRateResponse> = flow {
        val inputStream = context.assets.open("live-rates.json")
        val reader = InputStreamReader(inputStream)
        val response = gson.fromJson(reader, LiveRateResponse::class.java)
        reader.close()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getWalletBalances(): Flow<WalletBalanceResponse> = flow {
        val inputStream = context.assets.open("wallet-balance.json")
        val reader = InputStreamReader(inputStream)
        val response = gson.fromJson(reader, WalletBalanceResponse::class.java)
        reader.close()
        emit(response)
    }.flowOn(Dispatchers.IO)
}