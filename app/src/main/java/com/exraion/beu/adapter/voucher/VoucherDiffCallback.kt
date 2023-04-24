package com.exraion.beu.adapter.voucher

import com.exraion.beu.base.BaseDiffUtil
import com.exraion.beu.model.VoucherList

class VoucherDiffCallback(
    oldList: List<VoucherList>,
    newList: List<VoucherList>
): BaseDiffUtil<VoucherList, String>(oldList, newList) {
    override fun VoucherList.getItemIdentifier(): String = voucherId

}