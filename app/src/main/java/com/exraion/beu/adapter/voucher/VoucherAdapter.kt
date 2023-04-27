package com.exraion.beu.adapter.voucher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.exraion.beu.R
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListVoucherBinding
import com.exraion.beu.model.VoucherList
import com.exraion.beu.ui.voucher.VoucherFragmentDirections
import com.exraion.beu.util.VoucherCategory

class VoucherAdapter: BaseRecyclerViewAdapter<ItemListVoucherBinding, VoucherList>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListVoucherBinding {
        return ItemListVoucherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override val diffUtilBuilder: (List<VoucherList>, List<VoucherList>) -> DiffUtil.Callback?
        get() = { old, new ->
            VoucherDiffCallback(old, new)
        }

    override val binder: (VoucherList, ItemListVoucherBinding) -> Unit = { item, binding ->
        binding.apply {
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

        itemView.setOnClickListener {
            itemView.findNavController().navigate(
                VoucherFragmentDirections.actionNavigationVoucherToNavigationDetailVoucher(item.voucherId)
            )
        }
    }
}