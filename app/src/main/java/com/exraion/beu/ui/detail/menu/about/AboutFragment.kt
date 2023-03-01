package com.exraion.beu.ui.detail.menu.about

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentAboutBinding
import com.exraion.beu.ui.detail.menu.DetailMenuViewModel
import com.exraion.beu.util.Constanta.ARG_MENU_ID
import com.exraion.beu.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : BaseFragment<FragmentAboutBinding>() {
    
    private val viewModel by sharedViewModel<DetailMenuViewModel>()
    private lateinit var menuId: String
    
    companion object {
        fun getInstance(menuId: String): AboutFragment {
            return AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MENU_ID, menuId)
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            menuId = it?.getString(ARG_MENU_ID) ?: ""
        }
    }
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentAboutBinding {
        return FragmentAboutBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentAboutBinding.binder() {
        lifecycleScope.launchWhenStarted {
            viewModel.menuDetail.collect { menuDetail ->
                if (menuDetail != null) {
                    tvMenuDescription.text = menuDetail.description
                    tvMenuEstimateTime.text = menuDetail.estimatedTime
                    tvMenuBenefit.text = menuDetail.benefit
                }
            }
        }
    }
    
}