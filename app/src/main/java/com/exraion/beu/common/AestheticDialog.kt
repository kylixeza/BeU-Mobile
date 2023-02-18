package com.exraion.beu.common

import android.app.Activity
import android.view.Gravity
import com.thecode.aestheticdialogs.*

fun Activity.buildAestheticDialog(
    condition: DialogType,
    title: String,
    message: String,
    action: (AestheticDialog.Builder) -> Unit
) =
    AestheticDialog.Builder(this, DialogStyle.FLASH, condition)
        .setTitle(title)
        .setMessage(message)
        .setAnimation(DialogAnimation.ZOOM)
        .setGravity(Gravity.CENTER)
        .setOnClickListener(object : OnDialogClickListener {
            override fun onClick(dialog: AestheticDialog.Builder) {
                action(dialog)
            }
        })
        .show()