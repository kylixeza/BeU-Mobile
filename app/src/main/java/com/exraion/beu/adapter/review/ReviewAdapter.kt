package com.exraion.beu.adapter.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListReviewBinding
import com.exraion.beu.model.Review

class ReviewAdapter: BaseRecyclerViewAdapter<ItemListReviewBinding, Review>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListReviewBinding {
        return ItemListReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
    
    override val diffUtilBuilder: (List<Review>, List<Review>) -> DiffUtil.Callback?
        get() = { _, _ -> null }
    
    override val binder: (Review, ItemListReviewBinding) -> Unit
        get() = { data, item ->
            item.apply {
                tvNameReviewer.text = data.name
                tvRating.text = data.rating.toString()
                ratingBar.rating = data.rating.toFloat()
            }
        }
}