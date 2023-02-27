package com.github.merlinths.swing.flow.util

import javax.swing.JComponent
import javax.swing.JPanel

/**
 * Executes [actions] with a receiver attached to a [JPanel],
 * to ensure the flow bindings are active.
 */
internal inline fun <Type: JComponent> withAttached(
    component: Type,
    actions: Type.() -> Unit
) {
    val parent = JPanel().apply {
        add(component)
    }

    component.actions()
    parent.removeAll()
}