package com.exraion.beu.adapter.profile_additional_settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListProfileAdditionalSettingsBinding
import com.exraion.beu.model.AdditionalSetting

class ProfileAdditionalSettingAdapter: BaseRecyclerViewAdapter<ItemListProfileAdditionalSettingsBinding, AdditionalSetting>() {

    lateinit var listener: ProfileAdditionalSettingListener

    override fun inflateViewBinding(parent: ViewGroup): ItemListProfileAdditionalSettingsBinding {
        return ItemListProfileAdditionalSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
    
    override val diffUtilBuilder: (List<AdditionalSetting>, List<AdditionalSetting>) -> DiffUtil.Callback?
        get() = { _, _ -> null }
    
    override val binder: (AdditionalSetting, ItemListProfileAdditionalSettingsBinding) -> Unit
        get() = { data, item ->
            item.apply {
                Glide.with(this.root).load(data.icon).into(ivIcon)
                tvTitle.text = data.title
            }

            itemView.setOnClickListener {
                listener.onProfileAdditionalSettingClicked(data.direction)
            }
        }
}

interface ProfileAdditionalSettingListener {
    fun onProfileAdditionalSettingClicked(direction: String)
}