package com.exraion.beu.ui.daily_check_in

import com.exraion.beu.base.BaseActivity
import com.exraion.beu.databinding.ActivityDailyCheckInBinding
import com.exraion.beu.util.ScreenOrientation

class DailyCheckInActivity : BaseActivity<ActivityDailyCheckInBinding>() {
    override fun inflateViewBinding(): ActivityDailyCheckInBinding =
        ActivityDailyCheckInBinding.inflate(layoutInflater)

    override fun determineScreenOrientation(): ScreenOrientation? = ScreenOrientation.PORTRAIT

    override fun ActivityDailyCheckInBinding.binder() {
        dailyCheckInAppBar.apply {
            tvTitle.text = "Daily Check-in"
            ivArrowBack.setOnClickListener { finish() }
            ivFavorite.hide()
        }
    }

}