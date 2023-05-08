package com.exraion.beu.ui.detail.order

import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.exraion.beu.R
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentOrderBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.isError
import com.exraion.beu.util.isNotNullThen
import com.exraion.beu.util.isSuccess
import com.exraion.beu.util.not
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.showWhen
import com.exraion.beu.util.then
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class OrderFragment : BaseFragment<FragmentOrderBinding>() {

    private val viewModel by activityViewModel<OrderViewModel>()
    private val args by navArgs<OrderFragmentArgs>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentOrderBinding =
        FragmentOrderBinding.inflate(layoutInflater, container, false)

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT

    override fun FragmentOrderBinding.binder() {
        viewModel.clearAllStates()
        viewModel.setPrice(args.totalPrice)
        viewModel.ingredients = args.ingredients.toList()
        viewModel.menuId = args.menuId

        appBarOrder.apply {
            ivFavorite.hide()
            tvTitle.text = "Order Details"
            ivArrowBack.setOnClickListener {
                findNavController().navigate(
                    OrderFragmentDirections.actionOrderDestinationToIngredientNavigation(args.menuId)
                )
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                includeBottomBarDetail.btnOrder.isEnabled = it.isError() or it.isSuccess()
                it.isSuccess() then {
                    findNavController().navigate(
                        OrderFragmentDirections.actionOrderDestinationToVerificationDestination()
                    )
                }
            }
        }

        lifecycleScope.launch { viewModel.user.collect {
                it isNotNullThen { user ->
                    tvNameAndPhone.text = "${user.name} | ${user.phoneNumber}"
                    tvAddress.text = user.location
                }
            }
        }

        lifecycleScope.launch {
            viewModel.price.collect {
                tvPrice.text = it.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.shippingCost.collect {
                tvShippingCost.text = it.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.discount.collect {
                tvDiscount.text = it.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.admin.collect {
                tvAdmin.text = it.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.total.collect {
                tvTotal.text = "Rp$it"
            }
        }

        lifecycleScope.launch {
            viewModel.isVoucherApplied.collect {
                tvVoucherSelection.apply {
                    typeface = it then {
                        ResourcesCompat.getFont(context, R.font.inter_semibold)!!
                    } otherwise { ResourcesCompat.getFont(context, R.font.montserrat)!! }
                    text = it then { "Voucher applied" } otherwise { "Select Voucher" }
                    setTextColor(
                        it then {
                            ResourcesCompat.getColor(resources, R.color.success_900, null)
                        } otherwise { ResourcesCompat.getColor(resources, R.color.neutral_900, null) }
                    )
                }
                ivChecklist showWhen it hideWhen not { it }
            }
        }

        includeBottomBarDetail.btnOrder.setOnClickListener {
            viewModel.postOrder()
        }

        ivArrowVoucher.setOnClickListener {
            OrderSelectionVoucherFragment().show(parentFragmentManager, OrderSelectionVoucherFragment::class.java.simpleName)
        }

    }

    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            OrderFragmentDirections.actionOrderDestinationToIngredientNavigation(args.menuId)
        )
    }
}