package com.github.merlinths.swing.flow.binding.shorthand

import com.github.merlinths.swing.flow.SwingFlow
import com.github.merlinths.swing.flow.lifecycle.ParentLifecycle
import javax.swing.JComponent

context (SwingFlow, Type)
fun <Type: JComponent> bind(
    config: SwingFlow.() -> Unit
) {
    this@SwingFlow.bind(
        lifecycle = ParentLifecycle(),
        config
    )
}

infix fun <Type: JComponent> Type.binds(
    config: context (SwingFlow) Type.() -> Unit
) = binds(
    lifecycle = ParentLifecycle(),
    config
)