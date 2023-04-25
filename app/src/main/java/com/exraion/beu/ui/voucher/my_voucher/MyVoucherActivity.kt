package com.exraion.beu.ui.voucher.my_voucher

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.exraion.beu.adapter.voucher.VoucherAdapter
import com.exraion.beu.base.BaseActivity
import com.exraion.beu.databinding.ActivityMyVoucherBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.UIState
import io.github.tonnyl.light.Light
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyVoucherActivity : BaseActivity<ActivityMyVoucherBinding>() {

    private val viewModel by viewModel<MyVoucherViewModel>()
    private val adapter by inject<VoucherAdapter>()

    override fun inflateViewBinding(): ActivityMyVoucherBinding {
        return ActivityMyVoucherBinding.inflate(layoutInflater)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation? {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun ActivityMyVoucherBinding.binder() {

        rvMyVoucher.apply {
            adapter = this@MyVoucherActivity.adapter
            layoutManager = LinearLayoutManager(this@MyVoucherActivity, LinearLayoutManager.VERTICAL, false)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                when(it) {
                    UIState.IDLE -> doNothing()
                    UIState.LOADING -> { }
                    UIState.SUCCESS -> { }
                    UIState.ERROR -> Light.error(root, viewModel.message, Light.LENGTH_SHORT).show()
                    UIState.EMPTY -> { }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.vouchers.collectLatest {
                adapter.submitList(it)
            }
        }
    }
}