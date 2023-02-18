package com.exraion.beu.ui.auth.login

import android.view.ViewGroup
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentLoginBinding
import com.exraion.beu.util.ScreenOrientation

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun inflateViewBinding(container: ViewGroup?): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater, container, false)
    
    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT
    
    override fun FragmentLoginBinding.binder() {
    
    }
    
}