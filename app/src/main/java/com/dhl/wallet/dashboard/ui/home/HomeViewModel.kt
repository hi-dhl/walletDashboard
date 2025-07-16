package com.dhl.wallet.dashboard.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import java.math.BigDecimal
import androidx.lifecycle.viewModelScope
import com.dhl.wallet.dashboard.data.repository.WalletRepositoryImpl
import com.dhl.wallet.dashboard.model.Currency
import com.dhl.wallet.dashboard.model.LiveRate
import com.dhl.wallet.dashboard.model.WalletBalance
import com.dhl.wallet.dashboard.model.WalletItem

class HomeViewModel: ViewModel() {
    private val repository by lazy(mode = LazyThreadSafetyMode.NONE) { WalletRepositoryImpl() }

    private val _walletItems = MutableStateFlow<List<WalletItem>>(emptyList())
    val walletItems: StateFlow<List<WalletItem>> = _walletItems.asStateFlow()

    private val _totalUsdValue = MutableStateFlow(0.0)
    val totalUsdValue: StateFlow<Double> = _totalUsdValue.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadWalletData()
    }

    private fun loadWalletData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                combine(
                    repository.getCurrencies(),
                    repository.getLiveRates(),
                    repository.getWalletBalances()
                ) { currencies, liveRates, walletBalances ->
                    processWalletData(currencies, liveRates, walletBalances)
                }.collect { walletItems ->
                    _walletItems.value = walletItems
                    _totalUsdValue.value = walletItems.sumOf { it.usdValue }
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun processWalletData(
        currencies: List<Currency>,
        liveRates: List<LiveRate>,
        walletBalances: List<WalletBalance>
    ): List<WalletItem> {
        val currencyMap = currencies.associateBy { it.coin_id }
        val rateMap = liveRates.associateBy { it.from_currency }
        return walletBalances.mapNotNull { balance ->
            val currency = currencyMap[balance.currency]
            val liveRate = rateMap[balance.currency]

            if (currency != null) {
                val usdValue = calculateUsdValue(balance.amount, liveRate)
                WalletItem(
                    currency = currency,
                    balance = balance.amount,
                    usdValue = usdValue,
                    liveRate = liveRate
                )
            } else null
        }.sortedByDescending { it.usdValue }
    }

    private fun calculateUsdValue(amount: Double, liveRate: LiveRate?): Double {
        val rate = liveRate?.rates?.firstOrNull()?.rate?.toDoubleOrNull() ?: 0.0
        return BigDecimal(amount * rate)
            .toDouble()
    }

    fun refresh() {
        loadWalletData()
    }
}