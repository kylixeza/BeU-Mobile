package com.exraion.beu.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exraion.beu.R
import com.exraion.beu.ui.detail.menu.DetailMenuViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<DetailMenuViewModel>()

    companion object {
        const val MENU_ID = "menu_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val menuId = intent.getStringExtra(MENU_ID)
        viewModel.setMenuId(menuId.orEmpty())
    }
}