package com.github.merlinths.swing.flow.optional

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import java.util.Optional

fun <Type> Flow<Optional<Type>>.takeIfPresent(): Flow<Type> =
    mapNotNull(Optional<Type>::orNull)