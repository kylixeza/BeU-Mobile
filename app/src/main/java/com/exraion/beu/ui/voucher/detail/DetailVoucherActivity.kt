package com.exraion.beu.ui.voucher.detail

import android.app.AlertDialog
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.exraion.beu.R
import com.exraion.beu.adapter.voucher.terms_conditions.VoucherTermsAndConditionsAdapter
import com.exraion.beu.base.BaseActivity
import com.exraion.beu.common.buildAestheticDialog
import com.exraion.beu.databinding.ActivityDetailVoucherBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.UIState
import com.exraion.beu.util.VoucherCategory
import com.thecode.aestheticdialogs.DialogType
import io.github.tonnyl.light.Light
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailVoucherActivity : BaseActivity<ActivityDetailVoucherBinding>() {

    private val viewModel by viewModel<DetailVoucherViewModel>()
    private val adapter by inject<VoucherTermsAndConditionsAdapter>()

    companion object {
        const val VOUCHER_ID = "voucher_id"
        const val REDEEM_BUTTON_SHOWS = "redeem_button_shows"
    }

    override fun inflateViewBinding(): ActivityDetailVoucherBinding =
        ActivityDetailVoucherBinding.inflate(layoutInflater)

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT

    override fun ActivityDetailVoucherBinding.binder() {
        val voucherId = intent.getStringExtra(VOUCHER_ID).orEmpty()
        val isRedeemButtonShown = intent.getBooleanExtra(REDEEM_BUTTON_SHOWS, true)
        viewModel.fetchVoucherDetail(voucherId)

        rvTermsAndConditions.apply {
            adapter = this@DetailVoucherActivity.adapter
            layoutManager = LinearLayoutManager(this@DetailVoucherActivity, LinearLayoutManager.VERTICAL, false)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                when(it) {
                    UIState.LOADING -> {
                        btnRedeem.visibility = View.INVISIBLE
                    }
                    UIState.SUCCESS -> {
                        if(isRedeemButtonShown)
                            btnRedeem.visibility = View.VISIBLE
                        else
                            btnRedeem.visibility = View.INVISIBLE
                    }
                    UIState.ERROR -> Light.error(this@binder.root, viewModel.message, Light.LENGTH_SHORT).show()
                    else -> doNothing()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.voucher.collectLatest {item ->
                if(item != null) {
                    voucherPreview.apply {
                        val isProduct = item.category == VoucherCategory.PRODUCT.category
                        containerVoucher.setBackgroundResource(
                            if(isProduct) R.drawable.bg_voucher_product else R.drawable.bg_voucher_shipping
                        )
                        ivIcon.setBackgroundResource(
                            if(isProduct) R.drawable.ic_product else R.drawable.ic_shipping
                        )
                        tvXpCost.text = "${item.xpCost}XP"
                        tvDescription.text = if (isProduct) {
                            "${item.discount}% off up to Rp${item.maximumDiscount}"
                        } else {
                            "Free shipping minimum spend Rp${item.minimumSpend}"
                        }
                        if (isProduct.not()) tvSubtitle.visibility = View.INVISIBLE
                        tvValidUntil.text = "Valid until ${item.validUntil}"
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.termsAndConditions.collectLatest { items ->
                adapter.submitList(items)
            }
        }

        btnRedeem.setOnClickListener {
            AlertDialog.Builder(this@DetailVoucherActivity)
                .setTitle("Redeem Voucher")
                .setMessage("Are you sure you want to redeem this voucher?")
                .setPositiveButton("Yes") { dialog, _ ->
                    viewModel.checkIsXpEnough()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        lifecycleScope.launch {
            viewModel.isXpEnough.collect {
                if(it != null) {
                    if (it) {
                        viewModel.redeemVoucher()
                    } else {
                        Light.error(binding.root, "Your XP is not enough", Light.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isXpSuccessfullyRedeemed.collect {
                if(it != null) {
                    if (it.first) {
                        buildAestheticDialog(
                            DialogType.SUCCESS,
                            "Voucher Redeemed",
                            "You have successfully redeemed the voucher",
                        ) {
                            it.dismiss()
                            finish()
                        }
                    } else {
                        buildAestheticDialog(
                            DialogType.ERROR,
                            "Voucher Failed to Redeemed",
                            it.second.orEmpty(),
                        ) {
                            it.dismiss()
                        }
                    }
                }
            }
        }
    }

}