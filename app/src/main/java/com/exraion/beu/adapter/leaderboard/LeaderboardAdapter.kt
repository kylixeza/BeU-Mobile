package com.exraion.beu.adapter.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.exraion.beu.R
import com.exraion.beu.base.BaseRecyclerViewAdapter
import com.exraion.beu.databinding.ItemListLeaderboardBinding
import com.exraion.beu.model.Leaderboard

class LeaderboardAdapter: BaseRecyclerViewAdapter<ItemListLeaderboardBinding, Leaderboard>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListLeaderboardBinding {
        return ItemListLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
    
    override val diffUtilBuilder: (List<Leaderboard>, List<Leaderboard>) -> DiffUtil.Callback?
        get() = { oldList, newList ->
            LeaderboardDiffCallback(oldList, newList)
        }
    
    override val binder: (Leaderboard, ItemListLeaderboardBinding) -> Unit
        get() = { item, binding ->
            binding.apply {
                when(val rank = item.rank) {
                    1 -> binding.apply { buildLeaderboardItemList(item, rank, R.drawable.ic_rank_first) }
                    2 -> binding.apply { buildLeaderboardItemList(item, rank, R.drawable.ic_rank_second) }
                    3 -> binding.apply { buildLeaderboardItemList(item, rank, R.drawable.ic_rank_third) }
                    else -> binding.apply { rank.let { buildLeaderboardItemList(item, it) } }
                }
            }
        }
    
    private fun ItemListLeaderboardBinding.buildLeaderboardItemList(
        leaderboard: Leaderboard, rank: Int, resource: Int = 0
    ) {
        if (rank <= 3) {
            tvRank.visibility = View.INVISIBLE
            ivRank.setImageResource(resource)
        } else {
            tvRank.text = rank.toString()
            ivRank.visibility = View.INVISIBLE
        }
        Glide.with(itemView.context)
            .load(leaderboard.avatar)
            .placeholder(R.drawable.ilu_default_profile_picture)
            .circleCrop()
            .into(ivAvatar)
        tvAccountName.text = leaderboard.name
        tvXpPoints.text = String.format("${leaderboard.xp} XP")
    }
}