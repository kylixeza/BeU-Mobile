package com.exraion.beu.ui.home

import android.content.Intent
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.adapter.category.CategoryAdapter
import com.exraion.beu.adapter.menu_list_horizontal.MenuListHorizontalAdapter
import com.exraion.beu.adapter.menu_list_horizontal.MenuListHorizontalListener
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.common.initLinearHorizontal
import com.exraion.beu.databinding.FragmentHomeBinding
import com.exraion.beu.ui.detail.DetailActivity
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.isErrorDo
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.isSuccess
import com.exraion.beu.util.showWhen
import io.github.tonnyl.light.Light
import kotlinx.coroutines.launch
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

        val menuAdapterCallback = object : MenuListHorizontalListener {
            override fun onMenuClicked(menuId: String) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.MENU_ID, menuId)
                startActivity(intent)
            }
        }

        randomMenusAdapter.listener = menuAdapterCallback
        dietMenusAdapter.listener = menuAdapterCallback
        
        rvDefaultMenus.initLinearHorizontal(requireContext(), randomMenusAdapter)
        rvDietMenus.initLinearHorizontal(requireContext(), dietMenusAdapter)
        rvCategories.initLinearHorizontal(requireContext(), categoryAdapter)
        
        lifecycleScope.launch {
            viewModel.uiState.collect {
                pbHome showWhen it.isLoading() hideWhen it.isSuccess()
                tvDietMenus showWhen it.isSuccess() hideWhen it.isLoading()
                rvDefaultMenus showWhen it.isSuccess() hideWhen it.isLoading()
                rvDietMenus showWhen it.isSuccess() hideWhen it.isLoading()
                it.isErrorDo { Light.error(binding!!.root, viewModel.message, Light.LENGTH_SHORT).show() }
            }
        }
        
        lifecycleScope.launch {
            viewModel.user.collect {
                binding?.apply {
                    appBarHome.tvLocationPoint.text = it?.location
                    includeBeuPayHome.tvBeuPayBalance.text = it?.beUPay.toString()
                }
            }
        }
        
        lifecycleScope.launch {
            viewModel.menu.collect {
                randomMenusAdapter.submitList(it)
            }
        }
        
        lifecycleScope.launch {
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