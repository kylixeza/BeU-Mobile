package com.exraion.beu.ui.history

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.exraion.beu.adapter.history.HistoryAdapter
import com.exraion.beu.adapter.history.HistoryAdapterListener
import com.exraion.beu.base.BaseActivity
import com.exraion.beu.databinding.ActivityHistoryBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.isEmpty
import com.exraion.beu.util.isErrorDo
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.isSuccess
import com.exraion.beu.util.showWhen
import io.github.tonnyl.light.Light
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity<ActivityHistoryBinding>() {

    private val viewModel by viewModel<HistoryViewModel>()
    private val adapter by inject<HistoryAdapter>()

    override fun inflateViewBinding(): ActivityHistoryBinding =
        ActivityHistoryBinding.inflate(layoutInflater)

    override fun determineScreenOrientation(): ScreenOrientation? = ScreenOrientation.PORTRAIT

    override fun ActivityHistoryBinding.binder() {

        rvHistory.apply {
            adapter = this@HistoryActivity.adapter
            layoutManager = LinearLayoutManager(this@HistoryActivity, LinearLayoutManager.VERTICAL, false)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                pbHistory showWhen it.isLoading() hideWhen it.isSuccess()
                rvHistory showWhen it.isSuccess() hideWhen (it.isLoading() or it.isEmpty())
                tvHistoryEmpty showWhen it.isEmpty() hideWhen (it.isSuccess() or it.isLoading())
                it isErrorDo { Light.error(root, viewModel.message, Light.LENGTH_SHORT).show() }
            }
        }

        lifecycleScope.launch {
            viewModel.histories.collectLatest {
                adapter.submitList(it)
            }
        }

        adapter.listener = object : HistoryAdapterListener {
            override fun onCancelled(orderId: String) {
                viewModel.cancelOrder(orderId)
            }

            override fun onRateMenu(orderId: String, rating: Double) {
                viewModel.rateOrder(orderId, rating)
            }

            override fun onRepeatOrder(menuId: String) {

            }

        }

    }

}