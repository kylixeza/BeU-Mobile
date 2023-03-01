package com.exraion.beu.adapter.ingredient_tool

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListIngredientToolBinding

class IngredientToolAdapter: BaseRecyclerViewAdapter<ItemListIngredientToolBinding, String>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListIngredientToolBinding {
        return ItemListIngredientToolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
    
    override val diffUtilBuilder: (List<String>, List<String>) -> DiffUtil.Callback?
        get() = { old, new -> null }
    
    override val binder: (String, ItemListIngredientToolBinding) -> Unit
        get() = { data, item ->
            item.tvIngredientTool.text = data
        }
}