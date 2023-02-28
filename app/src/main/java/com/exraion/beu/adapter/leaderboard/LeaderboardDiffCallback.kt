package com.exraion.beu.adapter.leaderboard

import com.exraion.beu.base.BaseDiffUtil
import com.exraion.beu.model.Leaderboard

class LeaderboardDiffCallback(
    private val oldList: List<Leaderboard>,
    private val newList: List<Leaderboard>
): BaseDiffUtil<Leaderboard, String>(oldList, newList) {
    override fun Leaderboard.getItemIdentifier(): String {
        return this.name
    }
}