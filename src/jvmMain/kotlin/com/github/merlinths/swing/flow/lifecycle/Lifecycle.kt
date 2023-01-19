package com.github.merlinths.swing.flow.lifecycle

interface Lifecycle<Type> {
    context (Type)
    fun register(
        onBind: Runnable,
        onUnbind: Runnable
    )
}