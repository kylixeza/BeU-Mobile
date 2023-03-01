package com.exraion.beu.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.exraion.beu.ui.detail.menu.about.AboutFragment
import com.exraion.beu.ui.detail.menu.instruction.InstructionFragment
import com.exraion.beu.ui.detail.menu.review.ReviewFragment
import com.exraion.beu.util.Constanta

class DetailMenuPageAdapter (
    fm: FragmentManager,
    lifeCycle: Lifecycle,
    private val menuId: String
): FragmentStateAdapter(fm, lifeCycle) {


    override fun getItemCount(): Int = Constanta.TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = InstructionFragment.getInstance(menuId)
            1 -> fragment = AboutFragment.getInstance(menuId)
            2 -> fragment = ReviewFragment.getInstance(menuId)
        }
        return fragment as Fragment
    }
}