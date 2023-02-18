package com.exraion.beu.ui.recognition

import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.exraion.beu.R
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentImageRecognitionBinding
import com.exraion.beu.util.ScreenOrientation
import com.google.android.material.bottomnavigation.BottomNavigationView

class ImageRecognitionFragment : BaseFragment<FragmentImageRecognitionBinding>() {
    override fun inflateViewBinding(container: ViewGroup?): FragmentImageRecognitionBinding {
        return FragmentImageRecognitionBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT
    
    override fun FragmentImageRecognitionBinding.binder() {
    
    }
    
    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            ImageRecognitionFragmentDirections.actionNavigationImageRecognitionToNavigationHome()
        )
    }
}