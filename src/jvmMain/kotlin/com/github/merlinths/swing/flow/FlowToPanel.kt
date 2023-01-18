package com.github.merlinths.swing.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.swing.JComponent

context(CoroutineScope)
fun Flow<JComponent>.asObservingPanel() =
    asObservingPanel(this@CoroutineScope)

fun Flow<JComponent>.asObservingPanel(
    scope: CoroutineScope
) = ObservingPanel(
    scope = scope,
    content = this
)