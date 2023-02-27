package io.mths.swing.flow.lifecycle

import java.awt.Container
import java.awt.event.HierarchyEvent
import java.awt.event.HierarchyEvent.PARENT_CHANGED
import javax.swing.JComponent

class ParentLifecycle<Type: JComponent> : Lifecycle<Type> {

    context (Type)
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

    context (Type)
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