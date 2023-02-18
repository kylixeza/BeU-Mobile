package com.exraion.beu.ui.onboard.screen.third

import android.view.ViewGroup
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentThirdScreenBinding
import com.exraion.beu.ui.onboard.OnBoardingViewModel
import com.exraion.beu.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ThirdScreenFragment : BaseFragment<FragmentThirdScreenBinding>() {
    
    private val viewModel by sharedViewModel<OnBoardingViewModel>()
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentThirdScreenBinding {
        return FragmentThirdScreenBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentThirdScreenBinding.binder() {
    
    }
    
    override fun onBackPressedBehaviour() {
        viewModel.previousPageTo(1)
    }
    
}