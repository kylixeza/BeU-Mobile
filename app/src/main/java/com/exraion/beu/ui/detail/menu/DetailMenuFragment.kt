package com.exraion.beu.ui.detail.menu

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.exraion.beu.R
import com.exraion.beu.adapter.DetailMenuPageAdapter
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentDetailMenuBinding
import com.exraion.beu.util.Constanta
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.isError
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.isNotNullThen
import com.exraion.beu.util.isSuccess
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.showWhen
import com.exraion.beu.util.then
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.tabs.TabLayoutMediator
import io.github.tonnyl.light.Light
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class DetailMenuFragment : BaseFragment<FragmentDetailMenuBinding>() {
    
    private val viewModel by activityViewModel<DetailMenuViewModel>()
    private var exoPlayer: ExoPlayer? = null
    private lateinit var mediaDataSourceFactory: DataSource.Factory
    private lateinit var videoUrl: String
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentDetailMenuBinding {
        return FragmentDetailMenuBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentDetailMenuBinding.binder() {

        appBarDetailMenu.ivArrowBack.setOnClickListener {
            activity?.finish()
        }

        var menuId = ""

        lifecycleScope.launch {
            viewModel.menuId.collectLatest {
                it isNotNullThen {
                    menuId = it

                    val pagerAdapter = DetailMenuPageAdapter(
                        childFragmentManager,
                        lifecycle,
                        it
                    )

                    pagerAdapter.apply {
                        vpMenuDetail.adapter = this
                    }

                    TabLayoutMediator(tabDetail, vpMenuDetail) { tab, position ->
                        tab.text = Constanta.TAB_TITLES[position]
                    }.attach()
                }
            }
        }
        
        viewModel.getMenuDetail()

        lifecycleScope.launch {
            viewModel.uiState.collect {
                pbVideoPlayer showWhen it.isLoading() hideWhen it.isSuccess()
                appBarDetailMenu.ivFavorite showWhen it.isSuccess() hideWhen it.isLoading()
                appBarDetailMenu.tvTitle showWhen it.isSuccess() hideWhen it.isLoading()
                includeBottomBarDetail.availabilityStatus showWhen it.isSuccess() hideWhen it.isLoading()
                includeBottomBarDetail.btnOrder showWhen it.isSuccess() hideWhen it.isLoading()
                it.isError() then { Light.error(root, viewModel.message, Light.LENGTH_SHORT).show() }
            }
        }
        
        lifecycleScope.launch {
            viewModel.menuDetail.collect { menuDetail ->
                if (menuDetail != null) {
                    appBarDetailMenu.apply {
                        tvTitle.text = menuDetail.title
                    }
                    videoUrl = menuDetail.videoUrl
                    binding?.includeBottomBarDetail?.availabilityStatus?.text =
                        if (menuDetail.isAvailable) "Available" else "Not Available"
                    initializePlayer(menuDetail.videoUrl)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isFavorite.collect {
                Glide.with(requireContext()).load(
                    it then { R.drawable.ic_favorite_true } otherwise { R.drawable.ic_favorite_false }
                ).into(appBarDetailMenu.ivFavorite)
            }
        }

        appBarDetailMenu.ivFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }

        includeBottomBarDetail.btnOrder.setOnClickListener {
            findNavController().navigate(
                DetailMenuFragmentDirections.actionDetailMenuDestinationToIngredientNavigation(
                    menuId
                )
            )
        }
    }
    
    private fun initializePlayer(streamUrl: String) {
        mediaDataSourceFactory = DefaultDataSourceFactory(requireContext(), Util.getUserAgent(requireContext(), "mediaPlayerSample"))
        
        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
            MediaItem.fromUri(streamUrl))
        
        val mediaSourceFactory: MediaSource.Factory = DefaultMediaSourceFactory(mediaDataSourceFactory)
        
        val playbackListener = object: Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when(playbackState) {
                    ExoPlayer.STATE_READY -> {
                        exoPlayer?.playWhenReady = true
                        binding?.pbVideoPlayer?.hide()
                    }
                    ExoPlayer.STATE_BUFFERING -> binding?.pbVideoPlayer?.show()
                    else -> super.onPlaybackStateChanged(playbackState)
                }
            }
        }
        
        exoPlayer = ExoPlayer.Builder(requireContext())
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
        
        exoPlayer!!.addMediaSource(mediaSource)
        exoPlayer!!.addListener(playbackListener)
        exoPlayer!!.prepare()
        binding?.playerView?.player = exoPlayer
        binding?.playerView?.requestFocus()
    }
    
    private fun releasePlayer() {
        exoPlayer?.release()
    }
    
    override fun onResume() {
        super.onResume()
        
        if (Util.SDK_INT <= 23) initializePlayer(videoUrl)
    }
    
    override fun onPause() {
        super.onPause()
        
        if (Util.SDK_INT <= 23) releasePlayer()
    }
    
    override fun onStop() {
        super.onStop()
        
        if (Util.SDK_INT > 23) releasePlayer()
    }

    override fun onBackPressedBehaviour() {
        activity?.finish()
    }
}