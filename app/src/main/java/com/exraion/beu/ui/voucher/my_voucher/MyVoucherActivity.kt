package com.exraion.beu.ui.voucher.my_voucher

import com.exraion.beu.base.BaseActivity
import com.exraion.beu.databinding.ActivityMyVoucherBinding
import com.exraion.beu.util.ScreenOrientation

class MyVoucherActivity : BaseActivity<ActivityMyVoucherBinding>() {
    override fun inflateViewBinding(): ActivityMyVoucherBinding {
        return ActivityMyVoucherBinding.inflate(layoutInflater)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation? {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun ActivityMyVoucherBinding.binder() {
    
    }
    
}