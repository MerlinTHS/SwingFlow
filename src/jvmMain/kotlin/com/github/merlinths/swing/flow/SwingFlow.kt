package com.github.merlinths.swing.flow

import com.github.merlinths.swing.flow.lifecycle.Lifecycle
import com.github.merlinths.swing.flow.lifecycle.ParentLifecycle
import kotlinx.coroutines.*
import javax.swing.JComponent
import kotlin.coroutines.CoroutineContext

/**
 * Interface to access lifecycle-bound [CoroutineScope].
 *
 * In the context of a [JComponent] you can configure [bindings] via [invokes].
 *
 * @sample[ExampleEditor]
 */
interface SwingFlow : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Default

    context (Type)
    fun <Type> bindings(
        lifecycle: Lifecycle<Type>,
        config: FlowConfiguration
    ) {
        lifecycle.register(
            onBind = {
                launch(start = CoroutineStart.UNDISPATCHED) {
                    config()
                }
            },
            onUnbind = {
                coroutineContext.cancelChildren()
            }
        )
    }
}


context (SwingFlow, JComponent)
fun bindings(
    lifecycle: Lifecycle<JComponent> = ParentLifecycle(),
    config: FlowConfiguration
) {
    this@SwingFlow
        .bindings(lifecycle, config)
}