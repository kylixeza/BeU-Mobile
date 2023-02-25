package com.exraion.beu.ui.category

import com.exraion.beu.base.BaseActivity
import com.exraion.beu.databinding.ActivityCategoryBinding
import com.exraion.beu.util.ScreenOrientation

class CategoryActivity : BaseActivity<ActivityCategoryBinding>() {
    override fun inflateViewBinding(): ActivityCategoryBinding {
        return ActivityCategoryBinding.inflate(layoutInflater)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun ActivityCategoryBinding.binder() {
    
    }
    
}