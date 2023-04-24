package com.exraion.beu.ui.onboard.screen.first

import android.view.ViewGroup
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentFirstScreenBinding
import com.exraion.beu.ui.onboard.OnBoardingViewModel
import com.exraion.beu.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FirstScreenFragment : BaseFragment<FragmentFirstScreenBinding>() {

    private val viewModel by sharedViewModel<OnBoardingViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentFirstScreenBinding {
        return FragmentFirstScreenBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentFirstScreenBinding.binder() {
    
    }
    
    override fun onBackPressedBehaviour() {
        viewModel.prevPage()
    }
}