package com.exraion.beu.adapter.history

import com.exraion.beu.base.BaseDiffUtil
import com.exraion.beu.model.History

class HistoryDiffUtil(
    private val oldList: List<History>,
    private val newList: List<History>
): BaseDiffUtil<History, String>(oldList, newList) {
    override fun History.getItemIdentifier(): String = this.orderId
}