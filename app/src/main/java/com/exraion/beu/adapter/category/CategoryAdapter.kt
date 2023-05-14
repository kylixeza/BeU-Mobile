package com.exraion.beu.adapter.category

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListCategoryBinding
import com.exraion.beu.ui.category.CategoryActivity

class CategoryAdapter : BaseRecyclerViewAdapter<ItemListCategoryBinding, String>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListCategoryBinding {
        return ItemListCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
    
    override val diffUtilBuilder: (List<String>, List<String>) -> DiffUtil.Callback?
        get() = {_,_ -> null }
    
    override val binder: (String, ItemListCategoryBinding) -> Unit
        get() = { data, view ->
            view.tvCategory.text = data
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, CategoryActivity::class.java)
                intent.putExtra(CategoryActivity.CATEGORY, data)
                itemView.context.startActivity(intent)
            }
        }
    
}