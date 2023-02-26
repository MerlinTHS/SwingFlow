package com.github.merlinths.swing.flow.binding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.reflect.KFunction1
import kotlin.reflect.KMutableProperty

context (CoroutineScope)
operator fun <Type> KFunction1<Type, Unit>.invoke(
    supplyObservable: () -> Flow<Type>
) {
    bindTo(supplyObservable())
}

context (CoroutineScope)
operator fun <Type> KMutableProperty<Type>.invoke(
    supplyObservable: () -> Flow<Type>
) {
    /**
     * Since a call to KMutableProperty.Setter.call takes a vararg Any,
     * a local function serves as a bridge.
     */
    fun onChange(newValue: Type) {
        setter.call(newValue)
    }

    ::onChange bindTo supplyObservable()
}

context (CoroutineScope)
internal infix fun <Type> KFunction1<Type, Unit>.bindTo(
    observable: Flow<Type>
) {
    observable
        .onEach(this)
        .flowOn(Dispatchers.Default)
        .launchIn(this@CoroutineScope)
}