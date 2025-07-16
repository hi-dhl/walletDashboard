package com.dhl.wallet.dashboard.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dhl.wallet.dashboard.R
import com.dhl.wallet.dashboard.model.WalletItem
import java.text.NumberFormat
import java.util.Locale

class WalletAdapter : ListAdapter<WalletItem, WalletAdapter.WalletViewHolder>(WalletDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wallet, parent, false)
        return WalletViewHolder(view)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WalletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCurrencyName: TextView = itemView.findViewById(R.id.tvCurrencyName)
        private val tvBalance: TextView = itemView.findViewById(R.id.tvBalance)
        private val tvUsdValue: TextView = itemView.findViewById(R.id.tvUsdValue)

        fun bind(item: WalletItem) {
            tvCurrencyName.text = item.currency.name
            
            val balanceFormat = NumberFormat.getNumberInstance(Locale.US).apply {
                maximumFractionDigits = 8
                minimumFractionDigits = 0
            }
            tvBalance.text = "${balanceFormat.format(item.balance)} ${item.currency.symbol}"
            
            val usdFormat = NumberFormat.getCurrencyInstance(Locale.US)
            tvUsdValue.text = usdFormat.format(item.usdValue)
        }
    }

    class WalletDiffCallback : DiffUtil.ItemCallback<WalletItem>() {
        override fun areItemsTheSame(oldItem: WalletItem, newItem: WalletItem): Boolean {
            return oldItem.currency.coin_id == newItem.currency.coin_id
        }

        override fun areContentsTheSame(oldItem: WalletItem, newItem: WalletItem): Boolean {
            return oldItem == newItem
        }
    }
}