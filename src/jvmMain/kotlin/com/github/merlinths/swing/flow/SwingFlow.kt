package com.github.merlinths.swing.flow

import kotlinx.coroutines.*
import java.awt.Container
import java.awt.event.HierarchyEvent
import javax.swing.JComponent
import kotlin.coroutines.CoroutineContext

/**
 * Interface to access lifecycle-bound [CoroutineScope].
 *
 * In the context of a [JComponent] you can configure [bindings] via [invokes].
 *
 *  * Note that everything you launch using this scope is cancelled
 *  when the component is detached from its parent.
 *  * Bindings are launched whenever the component is added.
 *
 * @sample[ExampleEditor]
 */
interface SwingFlow : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Default

    context(JComponent)
    fun bindings(config: FlowConfiguration) {
        addHierarchyListener {
            if (it.isParentChange()) {
                handleParentChange(newParent = it.changedParent, config)
            }
        }
    }

    private fun HierarchyEvent.isParentChange() =
        (changeFlags and HierarchyEvent.PARENT_CHANGED.toLong()) != 0L

    context(JComponent)
    private fun handleParentChange(
        newParent: Container,
        config: FlowConfiguration
    ) {
        if (parent == newParent) {
            launch(start = CoroutineStart.UNDISPATCHED) {
                config()
            }
        } else {
            coroutineContext.cancelChildren()
        }
    }
}