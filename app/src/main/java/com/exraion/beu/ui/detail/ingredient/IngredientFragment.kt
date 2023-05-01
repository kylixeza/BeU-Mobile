package com.exraion.beu.ui.detail.ingredient

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.adapter.ingredient.IngredientAdapter
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentIngredientBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.isError
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.isSuccess
import com.exraion.beu.util.showWhen
import com.exraion.beu.util.then
import io.github.tonnyl.light.Light
import kotlinx.coroutines.launch
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
        
        lifecycleScope.launch {
            viewModel.uiState.collect {
                pbIngredient showWhen it.isLoading() hideWhen it.isSuccess()
                rvIngredient showWhen it.isSuccess() hideWhen it.isLoading()
                it.isError() then { Light.error(root, viewModel.message, Light.LENGTH_SHORT).show() }
            }
        }
        
        lifecycleScope.launch {
            viewModel.ingredients.collect { ingredient ->
                adapter.submitList(ingredient)
            }
        }
        
    }
}