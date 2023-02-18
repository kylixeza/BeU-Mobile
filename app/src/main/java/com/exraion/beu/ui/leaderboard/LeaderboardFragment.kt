package com.exraion.beu.ui.leaderboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.exraion.beu.R
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentLeaderboardBinding
import com.exraion.beu.util.ScreenOrientation

class LeaderboardFragment : BaseFragment<FragmentLeaderboardBinding>() {
    override fun inflateViewBinding(container: ViewGroup?): FragmentLeaderboardBinding {
        return FragmentLeaderboardBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentLeaderboardBinding.binder() {
    
    }
    
    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            LeaderboardFragmentDirections.actionNavigationLeaderboardToNavigationHome()
        )
    }
    
}