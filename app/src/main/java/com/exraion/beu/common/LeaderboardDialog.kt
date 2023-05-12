package com.exraion.beu.common

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.exraion.beu.R
import com.exraion.beu.databinding.DialogRankBinding
import com.exraion.beu.model.Leaderboard

@SuppressLint("InflateParams")
fun Context.buildLeaderboardDialog(
    rankBinding: DialogRankBinding,
    data: Leaderboard?
) = Dialog(this).apply {
    
    if(data == null)
        setContentView(rankBinding.root)
    
    with(rankBinding) {
        Glide.with(this@buildLeaderboardDialog)
            .load(data?.avatar)
            .placeholder(R.drawable.ilu_default_profile_picture)
            .into(rankBinding.ivProfile)
        tvDescRank.text = getString(R.string.leaderboard_dialog_description, data?.rank, data?.xp)
        btnOk.setOnClickListener { dismiss() }
        ivClose.setOnClickListener { dismiss() }
    }
    setContentView(rankBinding.root)
    setCanceledOnTouchOutside(false)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val metrics = resources.displayMetrics
    val width = metrics.widthPixels
    window?.setLayout(6 * width / 7, LinearLayout.LayoutParams.WRAP_CONTENT)
}