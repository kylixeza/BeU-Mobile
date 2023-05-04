package com.exraion.beu.ui.detail.order

import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.R
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentOrderBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.isNotNullThen
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.then
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class OrderFragment : BaseFragment<FragmentOrderBinding>() {

    private val viewModel by activityViewModel<OrderViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentOrderBinding =
        FragmentOrderBinding.inflate(layoutInflater, container, false)

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT

    override fun FragmentOrderBinding.binder() {
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
                        ResourcesCompat.getFont(context, R.font.inter_semibold)
                    } otherwise { ResourcesCompat.getFont(context, R.font.montserrat) }
                    text = it then { "Voucher applied" } otherwise { "Select Voucher" }
                    setTextColor(
                        it then {
                            ResourcesCompat.getColor(resources, R.color.success_900, null)
                        } otherwise { ResourcesCompat.getColor(resources, R.color.neutral_900, null) }
                    )
                }
            }
        }
    }
}