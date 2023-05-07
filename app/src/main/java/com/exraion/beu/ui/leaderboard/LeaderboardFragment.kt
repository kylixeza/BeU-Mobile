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
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.isErrorDo
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.isSuccess
import com.exraion.beu.util.showWhen
import io.github.tonnyl.light.Light
import kotlinx.coroutines.launch
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
        rvLeaderboard.initLinearVertical(requireContext(), adapter)
        
        lifecycleScope.launch {
            viewModel.uiState.collect {
                appBarLeaderboard.ivInfo showWhen it.isSuccess() hideWhen it.isLoading()
                pbLeaderboard showWhen it.isLoading() hideWhen it.isSuccess()
                rvLeaderboard showWhen it.isSuccess() hideWhen it.isLoading()
                it.isErrorDo { Light.error(root, viewModel.message, Light.LENGTH_SHORT).show() }
            }
        }
        
        lifecycleScope.launch {
            viewModel.leaderboard.collect {
                adapter.submitList(it)
            }
        }
        
        lifecycleScope.launch {
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