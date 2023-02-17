package com.exraion.beu.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingViewPagerAdapter(
    fm: FragmentManager,
    lifeCycle: Lifecycle
    ): FragmentStateAdapter(fm, lifeCycle) {

    private val listOfFragments = ArrayList<Fragment>()

    fun setAllFragments(fragments: List<Fragment>) {
        listOfFragments.apply {
            clear()
            addAll(fragments)
        }
    }

    override fun getItemCount(): Int = listOfFragments.size

    override fun createFragment(position: Int): Fragment = listOfFragments[position]
}