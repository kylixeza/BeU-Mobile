package com.exraion.beu.ui.auth.register

import android.view.ViewGroup
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentRegisterBinding
import com.exraion.beu.util.ScreenOrientation

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override fun inflateViewBinding(container: ViewGroup?): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(layoutInflater, container, false)
    
    
    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT
    
    override fun FragmentRegisterBinding.binder() {
    
    }
    
}