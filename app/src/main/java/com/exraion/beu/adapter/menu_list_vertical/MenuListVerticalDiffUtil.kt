package com.exraion.beu.adapter.menu_list_vertical

import com.exraion.beu.base.BaseDiffUtil
import com.exraion.beu.model.MenuList

class MenuListVerticalDiffUtil(
    private val oldList: List<MenuList>,
    private val newList: List<MenuList>
): BaseDiffUtil<MenuList, String>(oldList, newList) {
    override fun MenuList.getItemIdentifier(): String = menuId
}