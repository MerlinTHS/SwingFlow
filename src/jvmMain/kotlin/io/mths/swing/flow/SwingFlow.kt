package io.mths.swing.flow

import io.mths.swing.flow.lifecycle.Lifecycle
import kotlinx.coroutines.*
import javax.swing.JComponent
import kotlin.coroutines.CoroutineContext

/**
 * Interface to access lifecycle-bound [CoroutineScope].
 *
 * In the context of a [JComponent] you can configure [bind].
 */
interface SwingFlow {
    val swingScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Default + SupervisorJob())

    context (Type)
    fun <Type> bind(
        lifecycle: Lifecycle<Type>,
        init: suspend CoroutineScope.() -> Unit
    ) {
        var job: Job? = null

        lifecycle.register(
            onBind = {
                job = swingScope.launch(start = CoroutineStart.UNDISPATCHED) {
                    init()
                }
            },
            onUnbind = {
                job?.cancelChildren()
            }
        )
    }
}

fun <Type> swingFlow(
    target: Type,
    init: context (SwingFlow) Type.() -> Unit
): Type =
    target.apply {
        init(object : SwingFlow {}, target)
    }
