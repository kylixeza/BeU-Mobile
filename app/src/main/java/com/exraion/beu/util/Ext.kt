package com.exraion.beu.util

fun doNothing() = Unit

infix fun <T> T?.isNotNullDo(doSomething: (T) -> Unit): T? {
    if (this != null) doSomething(this)
    return this
}

infix fun <T> T?.isNullDo(doSomething: () -> Unit): T? {
    if (this == null) doSomething()
    return this
}

infix fun<T> Boolean.then(block: () -> T): ThenOtherwise<T> {
    var call: T? = null
    if (this) call = block()
    return ThenOtherwise(this, call)
}

infix fun<T: Any> ThenOtherwise<T>.otherwise(block: () -> T): T =
    if (!currentBooleanState) block()
    else data!!


data class ThenOtherwise<T>(
    val currentBooleanState: Boolean,
    val data: T?
)