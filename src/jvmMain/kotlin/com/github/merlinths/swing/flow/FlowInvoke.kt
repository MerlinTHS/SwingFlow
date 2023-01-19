package com.github.merlinths.swing.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KFunction1

context(CoroutineScope)
infix fun <Type> Flow<Type>.invokes(processor: KFunction1<Type, Unit>) {
    onEach(processor::invoke)
        .flowOn(Dispatchers.Default)
        .launchIn(this@CoroutineScope)
}