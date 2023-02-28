package com.exraion.beu.di

import com.exraion.beu.adapter.category.CategoryAdapter
import com.exraion.beu.adapter.leaderboard.LeaderboardAdapter
import com.exraion.beu.adapter.menu_list_horizontal.MenuListHorizontalAdapter
import com.exraion.beu.ui.auth.login.LoginViewModel
import com.exraion.beu.ui.auth.register.RegisterViewModel
import com.exraion.beu.ui.home.HomeViewModel
import com.exraion.beu.ui.leaderboard.LeaderboardViewModel
import com.exraion.beu.ui.onboard.OnBoardingViewModel
import com.exraion.beu.ui.profile.ProfileViewModel
import com.exraion.beu.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { OnBoardingViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { LeaderboardViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}

val adapterModule = module {
    factory { MenuListHorizontalAdapter() }
    factory { CategoryAdapter() }
    factory { LeaderboardAdapter() }
}