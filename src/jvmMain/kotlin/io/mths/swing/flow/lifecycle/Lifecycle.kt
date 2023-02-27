package io.mths.swing.flow.lifecycle

interface Lifecycle<Type> {
    context (Type)
    fun register(
        onBind: Runnable,
        onUnbind: Runnable
    )
}