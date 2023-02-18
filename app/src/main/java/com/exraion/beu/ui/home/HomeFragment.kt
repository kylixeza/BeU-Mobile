package com.exraion.beu.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exraion.beu.R
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentHomeBinding
import com.exraion.beu.util.ScreenOrientation

class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    override fun inflateViewBinding(container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentHomeBinding.binder() {
    
    }
    
    override fun onBackPressedBehaviour() {
        activity?.finish()
    }
}