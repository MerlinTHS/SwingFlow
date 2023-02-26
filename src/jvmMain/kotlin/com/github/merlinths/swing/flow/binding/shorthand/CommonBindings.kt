package com.github.merlinths.swing.flow.binding.shorthand

import com.github.merlinths.swing.flow.lifecycle.Lifecycle
import com.github.merlinths.swing.flow.swingFlow
import kotlinx.coroutines.CoroutineScope

fun <Type> Type.binds(
    lifecycle: Lifecycle<Type>,
    init: context (CoroutineScope) Type.() -> Unit
): Type =
    swingFlow(this) {
        bind(lifecycle) {
            init(this@bind, this@binds)
        }
    }