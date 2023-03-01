package com.exraion.beu.ui.detail.menu

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.R
import com.exraion.beu.adapter.DetailMenuPageAdapter
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentDetailMenuBinding
import com.exraion.beu.util.Constanta
import com.exraion.beu.util.ScreenOrientation
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
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailMenuFragment : BaseFragment<FragmentDetailMenuBinding>() {
    
    private val viewModel by sharedViewModel<DetailMenuViewModel>()
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
        
        viewModel.getMenuDetail()
    
        val menuId = ""
        
        val pagerAdapter = DetailMenuPageAdapter(
            childFragmentManager,
            lifecycle,
            menuId
        )
        
        pagerAdapter.apply {
            vpMenuDetail.adapter = this
        }
    
        TabLayoutMediator(tabDetail, vpMenuDetail) { tab, position ->
            tab.text = Constanta.TAB_TITLES[position]
        }.attach()
        
        lifecycleScope.launchWhenStarted {
            viewModel.menuDetail.collect { menuDetail ->
                if (menuDetail != null) {
                    appBarDetailMenu.apply {
                        tvTitle.text = menuDetail.title
                        ivFavorite.setImageResource(
                            if (menuDetail.isFavorite) R.drawable.ic_favorite_true else R.drawable.ic_favorite_false
                        )
                    }
                    videoUrl = menuDetail.videoUrl
                    binding?.includeBottomBarDetail?.availabilityStatus?.text =
                        if (menuDetail.isAvailable) "Available" else "Not Available"
                    initializePlayer(menuDetail.videoUrl)
                }
            }
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
    
}