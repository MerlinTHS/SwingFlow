package com.github.merlinths.swing.flow

import com.github.merlinths.swing.flow.lifecycle.Lifecycle
import com.github.merlinths.swing.flow.lifecycle.ParentLifecycle
import kotlinx.coroutines.*
import javax.swing.JComponent
import kotlin.coroutines.CoroutineContext

/**
 * Interface to access lifecycle-bound [CoroutineScope].
 *
 * In the context of a [JComponent] you can configure [bind].
 *
 * @sample[ExampleEditor]
 */
interface SwingFlow : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Default

    context (Type)
    fun <Type> bind(
        lifecycle: Lifecycle<Type>,
        config: SwingFlow.() -> Unit
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

fun <Type> swingFlow(
    target: Type,
    config: context (SwingFlow) Type.() -> Unit
): Type =
    target.apply {
        config.invoke(object : SwingFlow{}, target)
    }
