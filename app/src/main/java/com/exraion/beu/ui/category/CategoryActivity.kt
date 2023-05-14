package com.exraion.beu.ui.category

import androidx.lifecycle.lifecycleScope
import com.exraion.beu.adapter.menu_list_vertical.MenuListVerticalAdapter
import com.exraion.beu.base.BaseActivity
import com.exraion.beu.common.initLinearVertical
import com.exraion.beu.databinding.ActivityCategoryBinding
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.isErrorDo
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.isSuccess
import com.exraion.beu.util.showWhen
import io.github.tonnyl.light.Light
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryActivity : BaseActivity<ActivityCategoryBinding>() {

    private val viewModel by viewModel<CategoryViewModel>()
    private val adapter by inject<MenuListVerticalAdapter>()

    companion object {
        const val CATEGORY = "category"
    }

    override fun inflateViewBinding(): ActivityCategoryBinding {
        return ActivityCategoryBinding.inflate(layoutInflater)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun ActivityCategoryBinding.binder() {
        val category = intent.getStringExtra(CATEGORY).orEmpty()

        appBarCategory.apply {
            tvTitle.text = category
            ivArrowBack.setOnClickListener { finish() }
            ivFavorite.hide()
        }

        rvCategory.initLinearVertical(this@CategoryActivity, adapter)

        viewModel.fetchCategorizedMenus(category)

        lifecycleScope.launch {
            viewModel.uiState.collect {
                pbCategory showWhen it.isLoading() hideWhen it.isSuccess()
                rvCategory showWhen it.isSuccess() hideWhen it.isLoading()
                it.isErrorDo { Light.error(root, viewModel.message, Light.LENGTH_SHORT).show() }
            }
        }

        lifecycleScope.launch {
            viewModel.menus.collect {
                adapter.submitList(it)
            }
        }
    }
}