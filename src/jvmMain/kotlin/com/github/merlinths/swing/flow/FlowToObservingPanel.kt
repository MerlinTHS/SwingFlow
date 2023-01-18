package com.github.merlinths.swing.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.swing.JComponent

context(CoroutineScope)
fun Flow<JComponent>.asObservingPanel() =
    ObservingPanel(
        coroutineScope = this@CoroutineScope,
        content = this@Flow
    )