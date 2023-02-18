package com.exraion.beu.common

import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.deselectAllItems() {
    menu.setGroupCheckable(0, true, false)
    for (i in 0 until menu.size()) {
        menu.getItem(i).isChecked = false
    }
    menu.setGroupCheckable(0, true, true)
}