package com.exraion.beu.di

import com.exraion.beu.adapter.category.CategoryAdapter
import com.exraion.beu.adapter.history.HistoryAdapter
import com.exraion.beu.adapter.ingredient.IngredientAdapter
import com.exraion.beu.adapter.ingredient_tool.IngredientToolAdapter
import com.exraion.beu.adapter.leaderboard.LeaderboardAdapter
import com.exraion.beu.adapter.menu_list_horizontal.MenuListHorizontalAdapter
import com.exraion.beu.adapter.menu_list_vertical.MenuListVerticalAdapter
import com.exraion.beu.adapter.payment_method.PaymentMethodAdapter
import com.exraion.beu.adapter.profile_additional_settings.ProfileAdditionalSettingAdapter
import com.exraion.beu.adapter.review.ReviewAdapter
import com.exraion.beu.adapter.step.StepAdapter
import com.exraion.beu.adapter.voucher.VoucherAdapter
import com.exraion.beu.adapter.voucher.terms_conditions.VoucherTermsAndConditionsAdapter
import com.exraion.beu.ui.auth.login.LoginViewModel
import com.exraion.beu.ui.auth.register.RegisterViewModel
import com.exraion.beu.ui.category.CategoryViewModel
import com.exraion.beu.ui.detail.ingredient.IngredientViewModel
import com.exraion.beu.ui.detail.menu.DetailMenuViewModel
import com.exraion.beu.ui.detail.order.OrderViewModel
import com.exraion.beu.ui.history.HistoryViewModel
import com.exraion.beu.ui.home.HomeViewModel
import com.exraion.beu.ui.leaderboard.LeaderboardViewModel
import com.exraion.beu.ui.onboard.OnBoardingViewModel
import com.exraion.beu.ui.profile.ProfileViewModel
import com.exraion.beu.ui.recognition.ImageRecognitionViewModel
import com.exraion.beu.ui.splash.SplashViewModel
import com.exraion.beu.ui.voucher.VoucherViewModel
import com.exraion.beu.ui.voucher.detail.DetailVoucherViewModel
import com.exraion.beu.ui.voucher.my_voucher.MyVoucherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { OnBoardingViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { DetailMenuViewModel(get(), get()) }
    viewModel { IngredientViewModel(get()) }
    viewModel { OrderViewModel(get(), get(), get(), get()) }
    viewModel { LeaderboardViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { VoucherViewModel(get()) }
    viewModel { MyVoucherViewModel(get()) }
    viewModel { DetailVoucherViewModel(get(),get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { ImageRecognitionViewModel(get()) }
}

val adapterModule = module {
    factory { MenuListHorizontalAdapter() }
    factory { MenuListVerticalAdapter() }
    factory { CategoryAdapter() }
    factory { LeaderboardAdapter() }
    factory { IngredientToolAdapter() }
    factory { StepAdapter() }
    factory { IngredientAdapter() }
    factory { ReviewAdapter() }
    factory { ProfileAdditionalSettingAdapter() }
    factory { VoucherAdapter() }
    factory { VoucherTermsAndConditionsAdapter() }
    factory { HistoryAdapter() }
    factory { PaymentMethodAdapter() }
}