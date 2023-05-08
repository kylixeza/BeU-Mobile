package com.exraion.beu.ui.detail.ingredient

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.exraion.beu.adapter.ingredient.IngredientAdapter
import com.exraion.beu.adapter.ingredient.IngredientAdapterListener
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
    private val args by navArgs<IngredientFragmentArgs>()
    
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
            ivArrowBack.setOnClickListener {
                findNavController().navigate(
                    IngredientFragmentDirections.actionIngredientNavigationToDetailMenuDestination()
                )
            }
        }
        includeBottomBarDetail.btnOrder.isEnabled = false

        rvIngredient.apply {
            adapter = this@IngredientFragment.adapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        includeBottomBarDetail.apply {
            availability.text = "Payment"
            availabilityStatus.text = "Rp0"
        }

        viewModel.fetchIngredients(args.menuId)

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

        adapter.listener = object : IngredientAdapterListener {
            override fun onIngredientClicked(isCheckedAtLeastOne: Boolean, totalPrice: Int) {
                includeBottomBarDetail.apply {
                    availabilityStatus.text = "Rp$totalPrice"
                    btnOrder.isEnabled = isCheckedAtLeastOne
                }
            }
        }

        includeBottomBarDetail.btnOrder.setOnClickListener {
            val ingredients = adapter.getTrueCheckedIngredient()
            val action = IngredientFragmentDirections.actionIngredientNavigationToOrderDestination(
                ingredients.map { it.key }.toTypedArray(),
                ingredients.values.sum(),
                args.menuId
            )
            findNavController().navigate(action)
        }
    }

    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            IngredientFragmentDirections.actionIngredientNavigationToDetailMenuDestination()
        )
    }
}