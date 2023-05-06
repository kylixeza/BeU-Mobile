package com.exraion.beu.adapter.ingredient

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import com.exraion.beu.R
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListIngredientBinding
import com.exraion.beu.model.Ingredient
import com.exraion.beu.util.not
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.then

class IngredientAdapter: BaseRecyclerViewAdapter<ItemListIngredientBinding, Ingredient>() {

    //pair ingredient name and price
    private val checkedIngredient = mutableMapOf<String, Int>()
    lateinit var listener: IngredientAdapterListener
    
    fun getTrueCheckedIngredient() = checkedIngredient
    
    override fun inflateViewBinding(parent: ViewGroup): ItemListIngredientBinding {
        return ItemListIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
    
    override val diffUtilBuilder: (List<Ingredient>, List<Ingredient>) -> DiffUtil.Callback?
        get() = { old, new -> IngredientDiffCallback(old, new) }
    
    override val binder: (Ingredient, ItemListIngredientBinding) -> Unit
        @RequiresApi(Build.VERSION_CODES.M)
        get() = { data, item ->
            item.apply {
                tvIngredient.text = data.ingredient
                tvPrice.text = "Rp${data.price}"
                
                ivCheckbox.setOnClickListener {
                    checkedIngredient not { containsKey(data.ingredient) } then {
                        checkedIngredient[data.ingredient] = data.price
                        ivCheckbox.setImageResource(R.drawable.ic_checkbox_checked)
                        cvIngredient.setCardBackgroundColor(itemView.context.getColor(R.color.secondary_50))
                    } otherwise {
                        checkedIngredient.remove(data.ingredient)
                        ivCheckbox.setImageResource(R.drawable.ic_checkbox_unchecked)
                        cvIngredient.setCardBackgroundColor(itemView.context.getColor(R.color.white))
                    }

                    listener.onIngredientClicked(
                        checkedIngredient.isNotEmpty(),
                        checkedIngredient.values.sum()
                    )
                }
            }
        }
}

interface IngredientAdapterListener {
    fun onIngredientClicked(isCheckedAtLeastOne: Boolean, totalPrice: Int)
}