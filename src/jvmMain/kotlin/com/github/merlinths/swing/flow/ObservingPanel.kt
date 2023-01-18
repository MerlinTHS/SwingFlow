package com.github.merlinths.swing.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.awt.GridLayout
import javax.swing.JComponent
import javax.swing.JPanel

class ObservingPanel(
    scope: CoroutineScope,
    private val content: Flow<JComponent>
) : JPanel() {
    init {
        layout = GridLayout(0, 1)

        scope.observeContent()
    }

    private fun CoroutineScope.observeContent() =
        launch(Dispatchers.Default, CoroutineStart.UNDISPATCHED) {
            content.collectLatest(this@ObservingPanel::setContent)
        }

    private suspend fun setContent(
        newContent: JComponent
    ) = withContext(Dispatchers.Main.immediate) {
        removeAll()
        add(newContent)
    }
}