package com.exraion.beu.ui.voucher

import android.app.Dialog
import android.content.Intent
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.exraion.beu.R
import com.exraion.beu.adapter.voucher.VoucherAdapter
import com.exraion.beu.adapter.voucher.VoucherAdapterListener
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.common.buildAestheticDialog
import com.exraion.beu.common.buildLottieDialog
import com.exraion.beu.databinding.DialogLottieBinding
import com.exraion.beu.databinding.FragmentVoucherBinding
import com.exraion.beu.ui.voucher.detail.DetailVoucherActivity
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.isErrorDo
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.isNotNullThen
import com.exraion.beu.util.isSuccess
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.showWhen
import com.exraion.beu.util.then
import com.thecode.aestheticdialogs.DialogType
import io.github.tonnyl.light.Light
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class VoucherFragment : BaseFragment<FragmentVoucherBinding>() {

    private val viewModel by viewModel<VoucherViewModel>()
    private val productAdapter by inject<VoucherAdapter>()
    private val shippingAdapter by inject<VoucherAdapter>()

    private lateinit var lottieBinding: DialogLottieBinding
    private lateinit var lottieDialog: Dialog


    override fun inflateViewBinding(container: ViewGroup?): FragmentVoucherBinding {
        return FragmentVoucherBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentVoucherBinding.binder() {

        lottieBinding = DialogLottieBinding.inflate(layoutInflater)
        activity?.apply { lottieDialog = buildLottieDialog(lottieBinding, "loading_state.json") }

        val voucherAdapterListener = object : VoucherAdapterListener() {
            override fun onVoucherClick(voucherId: String) {
                val intent = Intent(requireContext(), DetailVoucherActivity::class.java)
                intent.putExtra(DetailVoucherActivity.VOUCHER_ID, voucherId)
                intent.putExtra(DetailVoucherActivity.REDEEM_BUTTON_SHOWS, true)
                startActivity(intent)
            }
        }

        shippingAdapter.listener = voucherAdapterListener
        productAdapter.listener = voucherAdapterListener

        appBarVoucher.btnSearch.setOnClickListener {
            viewModel.searchVoucher(appBarVoucher.svVoucher.query.toString())
        }

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
                tvShowMoreLessShippingVoucher showWhen it.isSuccess() hideWhen it.isLoading()
                tvShowMoreLessProductVoucher showWhen it.isSuccess() hideWhen it.isLoading()
                it.isErrorDo { Light.error(requireView(), viewModel.message, Light.LENGTH_SHORT).show() }
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

        lifecycleScope.launch {
            viewModel.isShippingExpended.collect {
                tvShowMoreLessShippingVoucher.text = it then { "Show Less" } otherwise { "Show More" }
                tvShowMoreLessShippingVoucher.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0, 0, it then { R.drawable.ic_arrow_up } otherwise { R.drawable.ic_arrow_down }, 0
                )
            }
        }

        lifecycleScope.launch {
            viewModel.isProductExpended.collect {
                tvShowMoreLessProductVoucher.text = it then { "Show Less" } otherwise { "Show More" }
                tvShowMoreLessProductVoucher.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0, 0, it then { R.drawable.ic_arrow_up } otherwise { R.drawable.ic_arrow_down }, 0
                )
            }
        }

        lifecycleScope.launch {
            viewModel.searchedVoucherUiState.collect {
                it.isLoading() then { lottieDialog.show() } otherwise { lottieDialog.dismiss() }
            }
        }

        lifecycleScope.launch {
            viewModel.voucherSecret.collect {
                it isNotNullThen {
                    it.isSuccessRedeemed then {
                        requireActivity().buildAestheticDialog(
                            DialogType.SUCCESS,
                            "Voucher Redeemed",
                            it.message,
                        ) {
                            it.dismiss()
                            viewModel.fetchAvailableVouchers()
                        }
                    } otherwise {
                        requireActivity().buildAestheticDialog(
                            DialogType.ERROR,
                            "Voucher Failed to Redeemed",
                            it.message,
                        ) {
                            it.dismiss()
                        }
                    }
                }
            }
        }

        appBarVoucher.ivMyVoucher.setOnClickListener {
            findNavController().navigate(
                VoucherFragmentDirections.actionNavigationVoucherToNavigationMyVoucher()
            )
        }

        tvShowMoreLessShippingVoucher.setOnClickListener {
            viewModel.expandShippingVouchers()
        }

        tvShowMoreLessProductVoucher.setOnClickListener {
            viewModel.expandProductVouchers()
        }
    }
    
    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            VoucherFragmentDirections.actionNavigationVoucherToNavigationHome()
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchAvailableVouchers()
    }
}