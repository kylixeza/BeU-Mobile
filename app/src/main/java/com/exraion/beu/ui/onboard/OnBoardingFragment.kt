package com.exraion.beu.ui.onboard

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.exraion.beu.R
import com.exraion.beu.adapter.OnBoardingViewPagerAdapter
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentOnBoardingBinding
import com.exraion.beu.ui.onboard.screen.first.FirstScreenFragment
import com.exraion.beu.ui.onboard.screen.second.SecondScreenFragment
import com.exraion.beu.ui.onboard.screen.third.ThirdScreenFragment
import com.exraion.beu.util.ScreenOrientation

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {
    
    private val args by navArgs<OnBoardingFragmentArgs>()
    lateinit var argSource: String
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentOnBoardingBinding {
        return FragmentOnBoardingBinding.inflate(layoutInflater, container, false)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun onCreateViewBehaviour(inflater: LayoutInflater, container: ViewGroup?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity?.window?.statusBarColor = resources.getColor(R.color.white)
        }
    }

    override fun FragmentOnBoardingBinding.binder() {
        argSource = args.source
    
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
        }
    
        binding?.apply {
            pageIndicatorView.setViewPager2(viewPager)
        }
    
        activity?.onBackPressedDispatcher?.addCallback {
            when(binding?.viewPager?.currentItem) {
                0 -> activity?.finish()
                1 -> binding?.viewPager?.currentItem = 0
                2 -> binding?.viewPager?.currentItem = 1
            }
        }
        
        binding?.apply {
            btnNext.setOnClickListener {
                when(binding?.viewPager?.currentItem) {
                    0 -> binding?.viewPager?.currentItem = 1
                    1 -> binding?.viewPager?.currentItem = 2
                    2 -> view?.findNavController()?.navigate(
                        OnBoardingFragmentDirections.actionOnBoardingDestinationToLoginFragment()
                    )
                }
            }
            
            btnSkip.setOnClickListener {
                view?.findNavController()?.navigate(
                    OnBoardingFragmentDirections.actionOnBoardingDestinationToLoginFragment()
                )
            }
        }
    
        if (argSource == "Login" || argSource == "Register") {
            binding?.viewPager?.currentItem = 3
        }
    }
}