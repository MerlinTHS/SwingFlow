package com.github.merlinths.swing.flow

import javax.swing.JComponent
import javax.swing.JPanel

/**
 * Executes [actions] with a receiver attached to a [JPanel],
 * to ensure the flow bindings are active.
 */
internal inline fun JComponent.attachedScope(
    actions: JComponent.() -> Unit
) {
    val parent = JPanel()
        .apply {
            add(this@attachedScope)
        }

    this.actions()
    parent.removeAll()
}