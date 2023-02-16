package com.exraion.beu.ui.onboard

import android.view.ViewGroup
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentOnBoardingBinding
import com.exraion.beu.util.ScreenOrientation

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {
    override fun inflateViewBinding(container: ViewGroup?): FragmentOnBoardingBinding {
        return FragmentOnBoardingBinding.inflate(layoutInflater, container, false)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun FragmentOnBoardingBinding.binder() {

    }
}