package com.exraion.beu.ui.onboard

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.exraion.beu.adapter.OnBoardingViewPagerAdapter
import com.exraion.beu.animation.PopPageTransformer
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentOnBoardingBinding
import com.exraion.beu.ui.onboard.screen.first.FirstScreenFragment
import com.exraion.beu.ui.onboard.screen.second.SecondScreenFragment
import com.exraion.beu.ui.onboard.screen.third.ThirdScreenFragment
import com.exraion.beu.util.ScreenOrientation
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import reactivecircus.flowbinding.viewpager2.pageSelections

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {

    private val viewModel by sharedViewModel<OnBoardingViewModel>()
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentOnBoardingBinding {
        return FragmentOnBoardingBinding.inflate(layoutInflater, container, false)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun FragmentOnBoardingBinding.binder() {
    
        val listOfFragment = listOf(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment(),
        )
    
        val adapter = OnBoardingViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle
        )
    
        adapter.apply {
            setAllFragments(listOfFragment)
            binding?.viewPager?.adapter = this
            viewPager.setPageTransformer(PopPageTransformer())
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        pageIndicatorView.setViewPager2(viewPager)

        viewPager.pageSelections().onEach { viewModel.setPage(it.plus(1)) }
            .launchIn(lifecycleScope)

        lifecycleScope.launch {
            viewModel.page.collect {
                viewPager.currentItem = it.minus(1)
            }
        }

        lifecycleScope.launch {
            viewModel.isMaxPage.collect {
                if(it) {
                    findNavController().navigate(
                        OnBoardingFragmentDirections.actionOnBoardingDestinationToAuthDestination()
                    )
                    activity?.finish()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isMinPage.collect {
                if (it) activity?.finish()
            }
        }

        btnNext.setOnClickListener {
            viewModel.nextPage()
        }

        btnSkip.setOnClickListener {
            findNavController().navigate(
                OnBoardingFragmentDirections.actionOnBoardingDestinationToAuthDestination()
            )
            activity?.finish()
        }
    }
    
    override fun onBackPressedBehaviour() {
        activity?.finish()
    }
}