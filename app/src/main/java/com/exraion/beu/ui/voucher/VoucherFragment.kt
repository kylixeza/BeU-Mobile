package com.exraion.beu.ui.voucher

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.exraion.beu.adapter.voucher.VoucherAdapter
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentVoucherBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.UIState
import io.github.tonnyl.light.Light
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class VoucherFragment : BaseFragment<FragmentVoucherBinding>() {

    private val viewModel by viewModel<VoucherViewModel>()
    private val productAdapter by inject<VoucherAdapter>()
    private val shippingAdapter by inject<VoucherAdapter>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentVoucherBinding {
        return FragmentVoucherBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentVoucherBinding.binder() {

        rvShipping.apply {
            adapter = shippingAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        rvProduct.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                when(it) {
                    UIState.IDLE -> doNothing()
                    UIState.LOADING -> { }
                    UIState.SUCCESS -> { }
                    UIState.ERROR -> Light.error(requireView(), viewModel.message, Light.LENGTH_SHORT).show()
                    UIState.EMPTY -> { }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.shippingVoucher.collectLatest {
                shippingAdapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            viewModel.productVoucher.collectLatest {
                productAdapter.submitList(it)
            }
        }

        appBarVoucher.ivMyVoucher.setOnClickListener {
            findNavController().navigate(
                VoucherFragmentDirections.actionNavigationVoucherToNavigationMyVoucher()
            )
        }
    }
    
    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            VoucherFragmentDirections.actionNavigationVoucherToNavigationHome()
        )
    }
}