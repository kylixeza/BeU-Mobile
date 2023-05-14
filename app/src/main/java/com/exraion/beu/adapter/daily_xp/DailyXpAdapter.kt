package com.exraion.beu.adapter.daily_xp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.exraion.beu.R
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListCheckInBinding
import com.exraion.beu.model.DailyXp

class DailyXpAdapter(): BaseRecyclerViewAdapter<ItemListCheckInBinding, DailyXp>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListCheckInBinding {
        return ItemListCheckInBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    @SuppressLint("SetTextI18n")
    override val binderWithPosition: (DailyXp, ItemListCheckInBinding, Int) -> Unit = { data, binding, position ->
        binding.apply {
            tvXpEarned.text = "${data.dailyXp}"
            ivCheckInCondition.setImageResource(
                if(data.isTaken) R.drawable.ic_check_in_yes else R.drawable.ic_check_in_no
            )
            tvDay.text = "Day ${position + 1}"
        }
    }

    override val diffUtilBuilder: (List<DailyXp>, List<DailyXp>) -> DiffUtil.Callback = { old, new ->
        DailyXpDiffUtil(old, new)
    }
}