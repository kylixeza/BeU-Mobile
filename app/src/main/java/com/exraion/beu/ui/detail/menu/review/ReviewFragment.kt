package com.exraion.beu.ui.detail.menu.review

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.adapter.review.ReviewAdapter
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentReviewBinding
import com.exraion.beu.ui.detail.menu.DetailMenuViewModel
import com.exraion.beu.util.Constanta
import com.exraion.beu.util.ScreenOrientation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ReviewFragment : BaseFragment<FragmentReviewBinding>() {
    
    private val viewModel by sharedViewModel<DetailMenuViewModel>()
    private val reviewAdapter by inject<ReviewAdapter>()
    private lateinit var menuId: String
    
    companion object {
        fun getInstance(menuId: String): ReviewFragment {
            return ReviewFragment().apply {
                arguments = Bundle().apply {
                    putString(Constanta.ARG_MENU_ID, menuId)
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            menuId = it?.getString(Constanta.ARG_MENU_ID) ?: ""
        }
    }
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentReviewBinding {
        return FragmentReviewBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentReviewBinding.binder() {
        lifecycleScope.launchWhenStarted {
            viewModel.menuDetail.collect { menuDetail ->
                menuDetail?.apply {
                    tvNumberOfRating.text = "Rated by ${menuDetail.reviewsCount} People"
                    tvAverageRating.text = menuDetail.averageRating.toString()
                    reviewAdapter.submitList(reviews)
                }
            }
        }
    }
    
}