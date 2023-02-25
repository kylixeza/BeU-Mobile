package com.exraion.beu.ui.profile

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.exraion.beu.R
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentProfileBinding
import com.exraion.beu.util.ScreenOrientation

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override fun inflateViewBinding(container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentProfileBinding.binder() {
        
    }
    
    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            ProfileFragmentDirections.actionNavigationProfileToNavigationHome()
        )
    }
}