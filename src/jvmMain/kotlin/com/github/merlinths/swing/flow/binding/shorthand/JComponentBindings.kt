package com.github.merlinths.swing.flow.binding.shorthand

import com.github.merlinths.swing.flow.SwingFlow
import com.github.merlinths.swing.flow.lifecycle.ParentLifecycle
import kotlinx.coroutines.CoroutineScope
import javax.swing.JComponent

context (SwingFlow, Type)
fun <Type: JComponent> bind(
    init: suspend CoroutineScope.() -> Unit
) {
    this@SwingFlow.bind(lifecycle = ParentLifecycle(), init)
}

infix fun <Type: JComponent> Type.binds(
    init: context (CoroutineScope) Type.() -> Unit
) = binds(lifecycle = ParentLifecycle(), init)