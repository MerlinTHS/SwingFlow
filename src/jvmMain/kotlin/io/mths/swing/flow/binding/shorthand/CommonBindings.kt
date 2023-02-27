package io.mths.swing.flow.binding.shorthand

import io.mths.swing.flow.lifecycle.Lifecycle
import io.mths.swing.flow.swingFlow
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