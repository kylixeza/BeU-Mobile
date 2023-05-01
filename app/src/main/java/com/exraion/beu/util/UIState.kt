package com.exraion.beu.util

import android.view.View

enum class UIState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR,
    EMPTY
}

infix fun View.showWhen(condition: Boolean): View {
    if(condition) visibility = View.VISIBLE
    return this
}

infix fun View.disappearWhen(condition: Boolean): View {
    if(condition) visibility = View.GONE
    return this
}

infix fun View.hideWhen(condition: Boolean): View {
    if(condition) visibility = View.INVISIBLE
    return this
}

fun UIState.isLoading(): Boolean = (this == UIState.LOADING)
fun UIState.isSuccess(): Boolean = (this == UIState.SUCCESS)
fun UIState.isError(): Boolean = (this == UIState.ERROR)
fun UIState.isEmpty(): Boolean = (this == UIState.EMPTY)

infix fun UIState.isLoadingDo(block: () -> Unit) {
    if(this.isLoading()) block()
}

infix fun UIState.isSuccessDo(block: () -> Unit) {
    if(this.isSuccess()) block()
}

infix fun UIState.isErrorDo(block: () -> Unit) {
    if(this.isError()) block()
}

infix fun UIState.isEmptyDo(block: () -> Unit) {
    if(this.isEmpty()) block()
}