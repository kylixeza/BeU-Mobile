package com.exraion.beu.di

import com.exraion.beu.ui.onboard.OnBoardingViewModel
import com.exraion.beu.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { OnBoardingViewModel() }
}