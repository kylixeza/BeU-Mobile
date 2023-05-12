package com.exraion.beu.adapter.menu_list_vertical

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.exraion.beu.R
import com.exraion.beu.adapter.menu_list_horizontal.MenuListHorizontalListener
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListMenuVerticalBinding
import com.exraion.beu.model.MenuList

class MenuListVerticalAdapter: BaseRecyclerViewAdapter<ItemListMenuVerticalBinding, MenuList>() {

    lateinit var listener: MenuListVerticalListener

    override fun inflateViewBinding(parent: ViewGroup): ItemListMenuVerticalBinding =
        ItemListMenuVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override val diffUtilBuilder: (List<MenuList>, List<MenuList>) -> DiffUtil.Callback?
        get() = { oldList, newList -> MenuListVerticalDiffUtil(oldList, newList) }

    override val binder: (MenuList, ItemListMenuVerticalBinding) -> Unit
        @RequiresApi(Build.VERSION_CODES.M)
        get() = { data, binding ->
            binding.apply {
                tvFoodName.text = data.title
                Glide.with(itemView.context).load(data.image)
                    .transform(RoundedCorners(5))
                    .centerCrop()
                    .into(ivMenu)

                when(data.difficulty) {
                    "Easy" -> {
                        cvDifficulty.setCardBackgroundColor(itemView.context.getColor(R.color.success_50))
                        tvDifficulty.text = data.difficulty
                        tvDifficulty.setTextColor(itemView.context.getColor(R.color.success_900))
                    }
                    "Medium" -> {
                        cvDifficulty.setCardBackgroundColor(itemView.context.getColor(R.color.primary_50))
                        tvDifficulty.text = data.difficulty
                        tvDifficulty.setTextColor(itemView.context.getColor(R.color.primary_900))
                    }
                    "Hard" -> {
                        cvDifficulty.setCardBackgroundColor(itemView.context.getColor(R.color.error_50))
                        tvDifficulty.text = data.difficulty
                        tvDifficulty.setTextColor(itemView.context.getColor(R.color.error_900))
                    }
                }

                tvRangePrice.text = data.rangePrice
                tvRating.text = data.rating.toString()
                tvEstimatedTime.text = "${data.cookTime} min"

                ivFavorite.setImageResource(
                    if (data.isFavorite) R.drawable.ic_favorite_true
                    else R.drawable.ic_favorite_black
                )

                ivFavorite.setOnClickListener {
                    listener.onFavoriteClicked(data.menuId)
                }

                itemView.setOnClickListener {
                    listener.onMenuClicked(data.menuId)
                }
            }
        }
}

interface MenuListVerticalListener: MenuListHorizontalListener {
    fun onFavoriteClicked(menuId: String)
}