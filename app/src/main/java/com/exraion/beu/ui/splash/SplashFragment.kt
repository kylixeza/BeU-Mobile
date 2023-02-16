package com.exraion.beu.ui.splash

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.exraion.beu.R
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
                view?.apply {
                    when (it) {
                        SplashUIState.FirstRun -> {
                            findNavController().navigate(
                                SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment("Splash")
                            )
                        }
                        SplashUIState.NotFirstRun -> {}
                        SplashUIState.LoggedIn -> {
                            view?.findNavController()?.navigate(R.id.action_splashFragment_to_baseActivity)
                            activity?.finish()
                        }
                        SplashUIState.NotLoggedIn -> {
                            findNavController().navigate(R.id.action_splash_destination_to_loginFragment)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

}