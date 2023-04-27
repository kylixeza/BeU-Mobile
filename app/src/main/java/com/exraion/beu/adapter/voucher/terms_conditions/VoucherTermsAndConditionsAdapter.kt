package com.exraion.beu.adapter.voucher.terms_conditions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListVoucherTermsAndConditionsBinding

class VoucherTermsAndConditionsAdapter: BaseRecyclerViewAdapter<ItemListVoucherTermsAndConditionsBinding, String>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListVoucherTermsAndConditionsBinding {
        return ItemListVoucherTermsAndConditionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override val binderWithPosition: (String, ItemListVoucherTermsAndConditionsBinding, Int) -> Unit
        get() = { data, binding, position ->
            binding.tvNumber.text = "${position + 1}."
            binding.tvTermsAndConditions.text = data
        }

    override val diffUtilBuilder: (List<String>, List<String>) -> DiffUtil.Callback?
        get() = { _, _ -> null}
}