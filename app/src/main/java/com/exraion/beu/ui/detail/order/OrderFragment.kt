package com.exraion.beu.ui.detail.order

import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.exraion.beu.R
import com.exraion.beu.adapter.payment_method.PaymentMethodAdapter
import com.exraion.beu.adapter.payment_method.PaymentMethodListener
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.common.initLinearHorizontal
import com.exraion.beu.data.source.dummy.PaymentMethod
import com.exraion.beu.data.source.dummy.getPaymentMethods
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
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class OrderFragment : BaseFragment<FragmentOrderBinding>() {

    private val viewModel by activityViewModel<OrderViewModel>()
    private val paymentMethodAdapter by inject<PaymentMethodAdapter>()
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
        tvIngredientsValue.text = viewModel.ingredients.joinToString(", ")

        rvPaymentMethod.initLinearHorizontal(requireContext(), paymentMethodAdapter)
        paymentMethodAdapter.submitList(getPaymentMethods())
        viewModel.fetchMenuDetail()

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

        lifecycleScope.launch {
            viewModel.menuDetail.collect {
                it isNotNullThen { menu ->
                    Glide.with(requireContext())
                        .load(menu.image)
                        .transform(RoundedCorners(6))
                        .centerCrop()
                        .into(ivMenu)
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
                tvIngredientPrice.text = "Rp$it"
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
            OrderSelectionVoucherFragment().show(requireActivity().supportFragmentManager, OrderSelectionVoucherFragment::class.java.simpleName)
        }

        paymentMethodAdapter.listener = object : PaymentMethodListener {
            override fun onPaymentMethodClicked(paymentMethod: PaymentMethod?) {
                includeBottomBarDetail.btnOrder.isEnabled = paymentMethod != null
            }
        }
    }

    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            OrderFragmentDirections.actionOrderDestinationToIngredientNavigation(args.menuId)
        )
    }
}