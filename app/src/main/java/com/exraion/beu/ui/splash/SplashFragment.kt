package com.exraion.beu.ui.splash

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentSplashBinding
import com.exraion.beu.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val viewModel by viewModel<SplashViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentSplashBinding =
        FragmentSplashBinding.inflate(layoutInflater, container, false)

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT

    override fun FragmentSplashBinding.binder() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it) {
                    SplashUIState.FirstRun -> {
                        // Navigate to onboarding
                    }
                    SplashUIState.NotFirstRun -> {
                        // Navigate to login
                    }
                    SplashUIState.LoggedIn -> {
                        // Navigate to home
                    }
                    SplashUIState.NotLoggedIn -> {
                        // Navigate to login
                    }
                    else -> {

                    }
                }
            }
        }
    }

}