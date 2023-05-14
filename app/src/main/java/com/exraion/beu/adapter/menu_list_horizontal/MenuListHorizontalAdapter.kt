package com.exraion.beu.adapter.menu_list_horizontal

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.exraion.beu.R
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListMenuHorizontalBinding
import com.exraion.beu.model.MenuList
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.not
import com.exraion.beu.util.showWhen

class MenuListHorizontalAdapter: BaseRecyclerViewAdapter<ItemListMenuHorizontalBinding, MenuList>() {

    lateinit var listener: MenuListHorizontalListener

    override fun inflateViewBinding(parent: ViewGroup): ItemListMenuHorizontalBinding {
        return ItemListMenuHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
    
    override val diffUtilBuilder: (List<MenuList>, List<MenuList>) -> DiffUtil.Callback?
        get() = { old, new -> MenuListHorizontalDiffCallback(old, new) }
    
    override val binder: (MenuList, ItemListMenuHorizontalBinding) -> Unit
        @RequiresApi(Build.VERSION_CODES.M)
        get() = { item, view ->
            view.apply {
                tvFoodName.text = item.title
                Glide.with(itemView.context).load(item.image)
                    .transform(RoundedCorners(5))
                    .centerCrop()
                    .into(ivFood)
                
                when(item.difficulty) {
                    "Easy" -> {
                        cvDifficulty.setCardBackgroundColor(itemView.context.getColor(R.color.success_50))
                        tvDifficulty.text = item.difficulty
                        tvDifficulty.setTextColor(itemView.context.getColor(R.color.success_900))
                    }
                    "Medium" -> {
                        cvDifficulty.setCardBackgroundColor(itemView.context.getColor(R.color.primary_50))
                        tvDifficulty.text = item.difficulty
                        tvDifficulty.setTextColor(itemView.context.getColor(R.color.primary_900))
                    }
                    "Hard" -> {
                        cvDifficulty.setCardBackgroundColor(itemView.context.getColor(R.color.error_50))
                        tvDifficulty.text = item.difficulty
                        tvDifficulty.setTextColor(itemView.context.getColor(R.color.error_900))
                    }
                }
                
                tvRangePrice.text = item.rangePrice
                tvRating.text = item.rating.toString()
                tvCookTime.text = "${item.cookTime} min"

                root.setOnClickListener {
                    listener.onMenuClicked(item.menuId)
                }

                ivExclusiveTag showWhen item.isExclusive hideWhen not { item.isExclusive }
            }
        }
}

interface MenuListHorizontalListener {
    fun onMenuClicked(menuId: String)
}