package com.dhl.wallet.dashboard


import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object AppHelper {
    lateinit var mContext: Context

    fun init(context: Context) {
        this.mContext = context
    }
}