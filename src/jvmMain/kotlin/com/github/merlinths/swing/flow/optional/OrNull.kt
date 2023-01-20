package com.github.merlinths.swing.flow.optional

import java.util.Optional

fun <Type> Optional<Type>.orNull(): Type? =
    orElse(null)