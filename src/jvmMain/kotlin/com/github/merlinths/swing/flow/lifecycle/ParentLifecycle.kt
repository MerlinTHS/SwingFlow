package com.github.merlinths.swing.flow.lifecycle

import java.awt.Container
import java.awt.event.HierarchyEvent
import java.awt.event.HierarchyEvent.PARENT_CHANGED
import javax.swing.JComponent

class ParentLifecycle : Lifecycle<JComponent> {

    context (JComponent)
    override fun register(
        onBind: Runnable,
        onUnbind: Runnable
    ) {
        addHierarchyListener {
            if (it.isParentChange()) {
                handleParentChange(it.changedParent, onBind, onUnbind)
            }
        }
    }

    private fun HierarchyEvent.isParentChange() =
        (changeFlags and PARENT_CHANGED.toLong()) != 0L

    context (JComponent)
    private fun handleParentChange(
        newParent: Container,
        onBind: Runnable,
        onUnbind: Runnable
    ) {
        if (parent == newParent) {
            onBind.run()
        } else {
            onUnbind.run()
        }
    }
}