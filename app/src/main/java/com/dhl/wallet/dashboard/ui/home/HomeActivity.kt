package com.dhl.wallet.dashboard.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dhl.wallet.dashboard.R
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: WalletAdapter
    private lateinit var tvTotalValue: TextView
    private lateinit var tvError: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        
        initViews()
        setupRecyclerView()
        observeViewModel()
    }

    private fun initViews() {
        tvTotalValue = findViewById(R.id.tvTotalValue)
        tvError = findViewById(R.id.tvError)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun setupRecyclerView() {
        adapter = WalletAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        
        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.walletItems.collect { items ->
                adapter.submitList(items)
            }
        }

        lifecycleScope.launch {
            viewModel.totalUsdValue.collect { totalValue ->
                val format = NumberFormat.getCurrencyInstance(Locale.US)
                tvTotalValue.text = format.format(totalValue)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                swipeRefresh.isRefreshing = isLoading
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { error ->
                if (error != null) {
                    tvError.text = error
                    tvError.visibility = View.VISIBLE
                } else {
                    tvError.visibility = View.GONE
                }
            }
        }
    }
}