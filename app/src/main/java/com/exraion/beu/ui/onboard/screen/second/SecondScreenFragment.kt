package com.exraion.beu.ui.onboard.screen.second

import android.view.ViewGroup
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentSecondScreenBinding
import com.exraion.beu.ui.onboard.OnBoardingViewModel
import com.exraion.beu.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SecondScreenFragment : BaseFragment<FragmentSecondScreenBinding>() {
    
    private val viewModel by sharedViewModel<OnBoardingViewModel>()
    override fun inflateViewBinding(container: ViewGroup?): FragmentSecondScreenBinding {
        return FragmentSecondScreenBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentSecondScreenBinding.binder() {
    
    }
    
    override fun onBackPressedBehaviour() {
        viewModel.previousPageTo(0)
    }
    
}