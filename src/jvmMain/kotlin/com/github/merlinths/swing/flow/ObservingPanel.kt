package com.github.merlinths.swing.flow

import kotlinx.coroutines.flow.*
import javax.swing.JComponent
import javax.swing.JPanel


class ObservingPanel(
    private val content: Flow<JComponent>
) : JPanel(), SwingFlow {
    init {
        bindings {
            content invokes ::setContent
        }
    }

    fun setContent(newContent: JComponent) {
        removeAll()
        add(newContent)
    }
}

fun Flow<JComponent>.observeAsPanel() =
    ObservingPanel(this)