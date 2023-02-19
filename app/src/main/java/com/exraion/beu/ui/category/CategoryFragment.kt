package com.exraion.beu.ui.category

import android.view.ViewGroup
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentCategoryBinding
import com.exraion.beu.util.ScreenOrientation

class CategoryFragment: BaseFragment<FragmentCategoryBinding>() {
    override fun inflateViewBinding(container: ViewGroup?): FragmentCategoryBinding =
        FragmentCategoryBinding.inflate(layoutInflater, container, false)
    
    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT
    
    override fun FragmentCategoryBinding.binder() {
    
    }
    
    
    
}