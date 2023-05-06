package com.exraion.beu.adapter.history

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.exraion.beu.R
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListHistoryBinding
import com.exraion.beu.model.History
import com.exraion.beu.util.OrderStatus
import com.exraion.beu.util.disappear
import com.exraion.beu.util.isEqualTo
import com.exraion.beu.util.isGreaterThan
import com.exraion.beu.util.show
import com.exraion.beu.util.then

class HistoryAdapter: BaseRecyclerViewAdapter<ItemListHistoryBinding, History>() {

    lateinit var listener: HistoryAdapterListener

    override fun inflateViewBinding(parent: ViewGroup): ItemListHistoryBinding {
        return ItemListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override val binder: (History, ItemListHistoryBinding) -> Unit
        get() = { data, binding ->
            binding.apply {
                tvTitle.text = data.title
                Glide.with(itemView.context)
                    .load(data.image)
                    .transform(RoundedCorners(16))
                    .into(ivImgMenu)
                tvIngredients.text = data.ingredients.joinToString(", ") + "."
                tvStatus.text = data.status
                tvOrderTime.text = data.timeStamp

                when(data.status) {
                    OrderStatus.PROCESSED.status -> {
                        tvStatus.text = OrderStatus.PROCESSED.status
                        tvStatus.setTextColor(itemView.resources.getColor(R.color.primary_900))
                        cvStatus.setBackgroundColor(itemView.resources.getColor(R.color.primary_50))
                        btnDecision.apply {
                            text = "Cancel Order"
                            setTextColor(itemView.resources.getColor(R.color.error_900))
                            setBackgroundColor(itemView.resources.getColor(R.color.error_50))
                            setOnClickListener {
                                listener.onCancelled(data.orderId)
                            }
                        }
                    }

                    OrderStatus.DONE.status -> {
                        tvStatus.text = OrderStatus.DONE.status
                        tvStatus.setTextColor(itemView.resources.getColor(R.color.secondary_900))
                        cvStatus.setBackgroundColor(itemView.resources.getColor(R.color.secondary_50))
                        includeSendRating.apply {
                            root.show()
                            ratingBar.rating = data.starsGiven.toFloat()
                            data.starsGiven isGreaterThan 0 then { ratingBar.setIsIndicator(true) }

                            ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
                                if(fromUser and (data.starsGiven isEqualTo 0)) {
                                    AlertDialog.Builder(itemView.context)
                                        .setTitle("Rate this menu")
                                        .setMessage("Are you sure want to rate this menu with $rating stars?")
                                        .setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->
                                            listener.onRateMenu(data.orderId, rating.toDouble())
                                            dialogInterface.dismiss()
                                        }
                                        .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                                            dialogInterface.dismiss()
                                            ratingBar.rating = 0f
                                        }
                                        .show()
                                }
                            }
                        }
                        btnDecision.apply {
                            text = "Repeat Order"
                            setTextColor(itemView.resources.getColor(R.color.success_900))
                            setBackgroundColor(itemView.resources.getColor(R.color.success_50))
                            setOnClickListener {
                                listener.onRepeatOrder(data.menuId)
                            }
                        }
                    }

                    OrderStatus.CANCELLED.status -> {
                        tvStatus.text = OrderStatus.CANCELLED.status
                        tvStatus.setTextColor(itemView.resources.getColor(R.color.error_900))
                        cvStatus.setBackgroundColor(itemView.resources.getColor(R.color.error_50))
                        btnDecision.disappear()
                    }
                }
            }
        }

    override val diffUtilBuilder: (List<History>, List<History>) -> DiffUtil.Callback?
        get() = { old, new -> HistoryDiffUtil(old, new) }
}

interface HistoryAdapterListener {
    fun onCancelled(orderId: String)
    fun onRateMenu(orderId: String, rating: Double)
    fun onRepeatOrder(menuId: String)
}