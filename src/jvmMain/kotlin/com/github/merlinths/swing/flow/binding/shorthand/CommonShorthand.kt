package com.github.merlinths.swing.flow.binding.shorthand

import com.github.merlinths.swing.flow.SwingFlow
import com.github.merlinths.swing.flow.lifecycle.Lifecycle
import com.github.merlinths.swing.flow.swingFlow

fun <Type> Type.binds(
    lifecycle: Lifecycle<Type>,
    config: context (SwingFlow) Type.() -> Unit
): Type =
    swingFlow(this) {
        bind(lifecycle) {
            config.invoke(this@bind, this@binds)
        }
    }