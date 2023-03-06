package com.exraion.beu.adapter.ingredient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.exraion.beu.R
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListIngredientBinding
import com.exraion.beu.model.Ingredient

class IngredientAdapter: BaseRecyclerViewAdapter<ItemListIngredientBinding, Ingredient>() {
    
    private val checkedIngredient = mutableMapOf<String, Boolean>()
    
    fun getTrueCheckedIngredient() = checkedIngredient.filterValues { it }.keys.toList()
    
    override fun inflateViewBinding(parent: ViewGroup): ItemListIngredientBinding {
        return ItemListIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
    
    override val diffUtilBuilder: (List<Ingredient>, List<Ingredient>) -> DiffUtil.Callback?
        get() = { old, new -> IngredientDiffCallback(old, new) }
    
    override val binder: (Ingredient, ItemListIngredientBinding) -> Unit
        get() = { data, item ->
            item.apply {
                tvIngredient.text = data.ingredient
                tvPrice.text = "Rp${data.price}"
                
                ivCheckbox.setOnClickListener {
                    checkedIngredient[data.ingredient] = !checkedIngredient[data.ingredient]!!
                    ivCheckbox.setImageResource(if (checkedIngredient[data.ingredient]!!) {
                        R.drawable.ic_checkbox_checked
                    } else {
                        R.drawable.ic_checkbox_unchecked
                    })
                }
            }
        }
}