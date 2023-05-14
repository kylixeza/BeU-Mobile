package com.exraion.beu.ui.home

import android.app.Dialog
import android.content.Intent
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.adapter.category.CategoryAdapter
import com.exraion.beu.adapter.menu_list_horizontal.MenuListHorizontalAdapter
import com.exraion.beu.adapter.menu_list_horizontal.MenuListHorizontalListener
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.common.buildLottieDialog
import com.exraion.beu.common.initLinearHorizontal
import com.exraion.beu.databinding.DialogLottieBinding
import com.exraion.beu.databinding.FragmentHomeBinding
import com.exraion.beu.ui.daily_check_in.DailyCheckInActivity
import com.exraion.beu.ui.detail.DetailActivity
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.isErrorDo
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.isNotNullThen
import com.exraion.beu.util.isSuccess
import com.exraion.beu.util.isSuccessDo
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.showWhen
import com.exraion.beu.util.then
import io.github.tonnyl.light.Light
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    
    private val randomMenusAdapter by inject<MenuListHorizontalAdapter>()
    private val dietMenusAdapter by inject<MenuListHorizontalAdapter>()
    private val categoryAdapter by inject<CategoryAdapter>()
    private val viewModel by inject<HomeViewModel>()

    private lateinit var lottieBinding: DialogLottieBinding
    private lateinit var lottieDialog: Dialog

    override fun inflateViewBinding(container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentHomeBinding.binder() {

        lottieBinding = DialogLottieBinding.inflate(layoutInflater)
        activity?.apply { lottieDialog = buildLottieDialog(lottieBinding, "loading_state.json") }

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

        lifecycleScope.launch {
            viewModel.isDailyXpTaken.collect {
                it.isNotNullThen {isDailyXpTaken ->
                    if (isDailyXpTaken) {
                        Light.warning(root, "You have already taken your daily XP", Light.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.dailyXpUiState.collect {
                it.isLoading() then { lottieDialog.show() } otherwise { lottieDialog.dismiss() }
                it.isErrorDo { Light.error(binding!!.root, viewModel.message, Light.LENGTH_SHORT).show() }
                it.isSuccessDo {
                    val intent = Intent(requireContext(), DailyCheckInActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        
        categoryAdapter.submitList(viewModel.categories)

        includeBeuPayHome.btnCheckin.setOnClickListener {
            viewModel.takeDailyXp()
        }
    }
    
    override fun onBackPressedBehaviour() {
        activity?.finish()
    }
}