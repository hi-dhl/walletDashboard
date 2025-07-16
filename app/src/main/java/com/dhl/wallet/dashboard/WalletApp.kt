package com.dhl.wallet.dashboard

import android.app.Application

class WalletApp : Application(){
    override fun onCreate() {
        super.onCreate()
        AppHelper.init(this)
    }
}