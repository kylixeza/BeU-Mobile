package com.exraion.beu.common

import android.widget.TextView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import reactivecircus.flowbinding.android.widget.textChanges

suspend fun TextView.observeValue(collectInto: (String) -> Unit) {
    this.textChanges()
        .skipInitialValue()
        .map { it.toString() }
        .collectLatest { collectInto(it) }
}