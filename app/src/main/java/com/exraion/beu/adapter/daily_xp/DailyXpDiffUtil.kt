package com.exraion.beu.adapter.daily_xp

import com.exraion.beu.base.BaseDiffUtil
import com.exraion.beu.model.DailyXp

class DailyXpDiffUtil(
    private val oldList: List<DailyXp>,
    private val newList: List<DailyXp>
): BaseDiffUtil<DailyXp, String>(oldList, newList) {
    override fun DailyXp.getItemIdentifier(): String = this.dailyXpId
}