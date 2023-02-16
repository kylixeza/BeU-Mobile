package com.exraion.beu.base

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffUtil<ListType, ItemIdentifier>(
    private val oldList: List<ListType>,
    private val newList: List<ListType>
): DiffUtil.Callback() {

    abstract fun ListType.getItemIdentifier(): ItemIdentifier

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].getItemIdentifier() == newList[newItemPosition].getItemIdentifier()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}