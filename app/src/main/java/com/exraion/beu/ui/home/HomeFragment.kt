package com.exraion.beu.ui.home

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.adapter.category.CategoryAdapter
import com.exraion.beu.adapter.menu_list_horizontal.MenuListHorizontalAdapter
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.common.initLinearHorizontal
import com.exraion.beu.databinding.FragmentHomeBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.UIState
import io.github.tonnyl.light.Light
import org.koin.android.ext.android.inject

class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    
    private val randomMenusAdapter by inject<MenuListHorizontalAdapter>()
    private val dietMenusAdapter by inject<MenuListHorizontalAdapter>()
    private val categoryAdapter by inject<CategoryAdapter>()
    private val viewModel by inject<HomeViewModel>()
    override fun inflateViewBinding(container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentHomeBinding.binder() {
        
        rvDefaultMenus.initLinearHorizontal(requireContext(), randomMenusAdapter)
        rvDietMenus.initLinearHorizontal(requireContext(), dietMenusAdapter)
        rvCategories.initLinearHorizontal(requireContext(), categoryAdapter)
        
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when(it) {
                    UIState.IDLE -> doNothing()
                    UIState.LOADING -> { }
                    UIState.SUCCESS -> { }
                    UIState.ERROR -> Light.error(binding!!.root, viewModel.message, Light.LENGTH_SHORT).show()
                    UIState.EMPTY -> doNothing()
                }
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.user.collect {
                binding?.apply {
                    appBarHome.tvLocationPoint.text = it?.location
                    includeBeuPayHome.tvBeuPayBalance.text = it?.beUPay.toString()
                }
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.menu.collect {
                randomMenusAdapter.submitList(it)
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.dietMenus.collect {
                dietMenusAdapter.submitList(it)
            }
        }
        
        categoryAdapter.submitList(viewModel.categories)
    }
    
    override fun onBackPressedBehaviour() {
        activity?.finish()
    }
}