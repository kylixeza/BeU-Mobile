package com.exraion.beu.ui.onboard.screen.first

import android.view.ViewGroup
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentFirstScreenBinding
import com.exraion.beu.util.ScreenOrientation

class FirstScreenFragment : BaseFragment<FragmentFirstScreenBinding>() {
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentFirstScreenBinding {
        return FragmentFirstScreenBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentFirstScreenBinding.binder() {
    
    }
    
    override fun onBackPressedBehaviour() {
        activity?.finish()
    }
}