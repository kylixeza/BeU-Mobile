package com.exraion.beu.adapter.menu_list_horizontal

import com.exraion.beu.base.BaseDiffUtil
import com.exraion.beu.model.MenuList

class MenuListHorizontalDiffCallback(
    private val oldList: List<MenuList>,
    private val newList: List<MenuList>
): BaseDiffUtil<MenuList, String>(oldList, newList) {
    override fun MenuList.getItemIdentifier(): String {
        return this.menuId
    }
}