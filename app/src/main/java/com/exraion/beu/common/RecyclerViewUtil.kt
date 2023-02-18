package com.exraion.beu.common

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exraion.beu.base.BaseRecyclerViewAdapter

fun RecyclerView.initLinearHorizontal(context: Context, adapter: BaseRecyclerViewAdapter<*, *>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}

fun RecyclerView.initLinearVertical(context: Context, adapter: BaseRecyclerViewAdapter<*, *>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}

fun RecyclerView.initGridHorizontal(context: Context, adapter: BaseRecyclerViewAdapter<*, *>, spanCount: Int) {
    this.adapter = adapter
    this.layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.HORIZONTAL, false)
}

fun RecyclerView.initGridVertical(context: Context, adapter: BaseRecyclerViewAdapter<*, *>, spanCount: Int) {
    this.adapter = adapter
    this.layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
}
