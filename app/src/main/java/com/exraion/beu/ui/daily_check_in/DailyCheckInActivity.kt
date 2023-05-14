package com.exraion.beu.ui.daily_check_in

import android.app.Dialog
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.adapter.daily_xp.DailyXpAdapter
import com.exraion.beu.base.BaseActivity
import com.exraion.beu.common.buildLottieDialog
import com.exraion.beu.common.initLinearHorizontal
import com.exraion.beu.databinding.ActivityDailyCheckInBinding
import com.exraion.beu.databinding.DialogLottieBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.isErrorDo
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.then
import io.github.tonnyl.light.Light
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DailyCheckInActivity : BaseActivity<ActivityDailyCheckInBinding>() {

    private lateinit var lottieBinding: DialogLottieBinding
    private lateinit var lottieDialog: Dialog

    private val viewModel by viewModel<DailyCheckInViewModel>()
    private val adapter by inject<DailyXpAdapter>()

    override fun inflateViewBinding(): ActivityDailyCheckInBinding =
        ActivityDailyCheckInBinding.inflate(layoutInflater)

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT

    override fun ActivityDailyCheckInBinding.binder() {

        lottieBinding = DialogLottieBinding.inflate(layoutInflater)
        this@DailyCheckInActivity.apply { lottieDialog = buildLottieDialog(lottieBinding, "loading_state.json") }

        rvCheckIn.initLinearHorizontal(this@DailyCheckInActivity, adapter)

        dailyCheckInAppBar.apply {
            tvTitle.text = "Daily Check-in"
            ivArrowBack.setOnClickListener { finish() }
            ivFavorite.hide()
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                it.isLoading() then { lottieDialog.show() } otherwise { lottieDialog.dismiss() }
                it.isErrorDo { Light.error(root, viewModel.message, Light.LENGTH_SHORT).show() }
            }
        }

        lifecycleScope.launch {
            viewModel.dailyXps.collect {
                adapter.submitList(it)
            }
        }
    }

}