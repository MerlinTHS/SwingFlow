package com.github.merlinths.swing.flow.binding

import com.github.merlinths.swing.flow.SwingFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.reflect.KFunction1

context (SwingFlow)
operator fun <Type> Flow<Type>.get(setter: KFunction1<Type, Unit>) {
    onEach(setter)
        .flowOn(Dispatchers.Default)
        .launchIn(this@SwingFlow)
}