package com.exraion.beu.ui.detail.ingredient

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.adapter.ingredient.IngredientAdapter
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentIngredientBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.UIState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class IngredientFragment : BaseFragment<FragmentIngredientBinding>() {
    
    private val viewModel by viewModel<IngredientViewModel>()
    private val adapter by inject<IngredientAdapter>()
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentIngredientBinding {
        return FragmentIngredientBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentIngredientBinding.binder() {
        appBarIngredient.apply {
            tvTitle.text = "Order Details"
            ivFavorite.hide()
        }
        
        viewModel.fetchIngredients("")
        
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when(it) {
                    UIState.IDLE -> {}
                    UIState.LOADING -> {}
                    UIState.EMPTY -> {}
                    UIState.ERROR -> {}
                    UIState.SUCCESS -> {}
                }
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.ingredients.collect { ingredient ->
                adapter.submitList(ingredient)
            }
        }
        
    }
}