package com.exraion.beu.adapter.payment_method

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.exraion.beu.R
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.data.source.dummy.PaymentMethod
import com.exraion.beu.databinding.ItemListPaymentMethodBinding
import com.exraion.beu.util.isNotNullThen
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.then


class PaymentMethodAdapter: BaseRecyclerViewAdapter<ItemListPaymentMethodBinding, PaymentMethod>() {

    lateinit var listener: PaymentMethodListener
    private var paymentMethod: Pair<Int, PaymentMethod>? = null
    private var selectedPosition = RecyclerView.NO_POSITION

    override fun inflateViewBinding(parent: ViewGroup): ItemListPaymentMethodBinding =
        ItemListPaymentMethodBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override val diffUtilBuilder: (List<PaymentMethod>, List<PaymentMethod>) -> DiffUtil.Callback?
        get() = {_, _ -> null}

    override val binderWithPosition: (PaymentMethod, ItemListPaymentMethodBinding, Int) -> Unit
        get() = { data, binding, position ->
            binding.ivPaymentMethod.setImageResource(data.image)
            paymentMethod isNotNullThen {
                (selectedPosition == position) then {
                    paymentMethod = position to data
                    binding.containerPaymentMethod.setBackgroundResource(R.drawable.circle_white_selected)
                } otherwise {
                    binding.containerPaymentMethod.setBackgroundResource(R.drawable.circle_white)
                }
            }
            itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position
                paymentMethod = position to data
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                listener.onPaymentMethodClicked(paymentMethod?.second)
            }
        }
}

interface PaymentMethodListener {
    fun onPaymentMethodClicked(paymentMethod: PaymentMethod?)
}

