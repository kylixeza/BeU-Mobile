package com.exraion.beu.ui.voucher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.exraion.beu.R
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentVoucherBinding
import com.exraion.beu.util.ScreenOrientation

class VoucherFragment : BaseFragment<FragmentVoucherBinding>() {
    override fun inflateViewBinding(container: ViewGroup?): FragmentVoucherBinding {
        return FragmentVoucherBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentVoucherBinding.binder() {
    
    }
    
    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            VoucherFragmentDirections.actionNavigationVoucherToNavigationHome()
        )
    }
    
}