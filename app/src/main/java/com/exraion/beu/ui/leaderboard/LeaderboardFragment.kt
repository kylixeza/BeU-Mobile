package com.exraion.beu.ui.leaderboard

import android.app.Dialog
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.exraion.beu.adapter.leaderboard.LeaderboardAdapter
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.common.buildLeaderboardDialog
import com.exraion.beu.common.initLinearVertical
import com.exraion.beu.databinding.DialogRankBinding
import com.exraion.beu.databinding.FragmentLeaderboardBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.UIState
import io.github.tonnyl.light.Light
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeaderboardFragment : BaseFragment<FragmentLeaderboardBinding>() {
    
    private val viewModel by viewModel<LeaderboardViewModel>()
    private val adapter by inject<LeaderboardAdapter>()
    private lateinit var dialog: Dialog
    private lateinit var dialogRankBinding: DialogRankBinding
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentLeaderboardBinding {
        return FragmentLeaderboardBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentLeaderboardBinding.binder() {
        dialogRankBinding = DialogRankBinding.inflate(layoutInflater)
        rvleaderboard.initLinearVertical(requireContext(), adapter)
        
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when(it) {
                    UIState.IDLE -> doNothing()
                    UIState.LOADING -> this@binder.appBarLeaderboard.ivInfo.hide()
                    UIState.SUCCESS -> this@binder.appBarLeaderboard.ivInfo.show()
                    UIState.ERROR -> Light.error(binding!!.root, viewModel.message, Light.LENGTH_SHORT).show()
                    UIState.EMPTY -> doNothing()
                }
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.leaderboard.collect {
                adapter.submitList(it)
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.myRank.collect {
                dialog = requireContext().buildLeaderboardDialog(dialogRankBinding, it)
            }
        }
        
        appBarLeaderboard.ivInfo.setOnClickListener { dialog.show() }
    }
    
    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            LeaderboardFragmentDirections.actionNavigationLeaderboardToNavigationHome()
        )
    }
    
}