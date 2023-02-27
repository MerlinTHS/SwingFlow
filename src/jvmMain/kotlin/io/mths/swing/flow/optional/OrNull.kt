package io.mths.swing.flow.optional

import java.util.Optional

fun <Type> Optional<Type>.orNull(): Type? =
    orElse(null)