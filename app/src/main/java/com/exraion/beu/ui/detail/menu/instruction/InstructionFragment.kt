package com.exraion.beu.ui.detail.menu.instruction

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.adapter.ingredient_tool.IngredientToolAdapter
import com.exraion.beu.adapter.step.StepAdapter
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentInstructionBinding
import com.exraion.beu.ui.detail.menu.DetailMenuViewModel
import com.exraion.beu.ui.detail.menu.about.AboutFragment
import com.exraion.beu.util.Constanta
import com.exraion.beu.util.ScreenOrientation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InstructionFragment : BaseFragment<FragmentInstructionBinding>() {
    
    private val viewModel by sharedViewModel<DetailMenuViewModel>()
    private val ingredientAdapter by inject<IngredientToolAdapter>()
    private val toolAdapter by inject<IngredientToolAdapter>()
    private val stepAdapter by inject<StepAdapter>()
    private lateinit var menuId: String
    
    companion object {
        fun getInstance(menuId: String): InstructionFragment {
            return InstructionFragment().apply {
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
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentInstructionBinding {
        return FragmentInstructionBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentInstructionBinding.binder() {
        lifecycleScope.launchWhenCreated {
            viewModel.menuDetail.collect { menuDetail ->
                menuDetail?.apply {
                    ingredientAdapter.submitList(ingredients)
                    toolAdapter.submitList(tools)
                    stepAdapter.submitList(steps)
                }
            }
        }
    }

}