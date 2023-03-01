package com.exraion.beu.adapter.step

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListStepBinding

class StepAdapter: BaseRecyclerViewAdapter<ItemListStepBinding, String>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListStepBinding {
        return ItemListStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
    
    override val diffUtilBuilder: (List<String>, List<String>) -> DiffUtil.Callback?
        get() = { _, _ -> null }
    
    override val binder: (String, ItemListStepBinding) -> Unit
        get() = { data, item ->
            item.tvStep.text = data
        }
}