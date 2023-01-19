package com.github.merlinths.swing.flow

import kotlinx.coroutines.flow.Flow
import javax.swing.JPanel
import javax.swing.JTextArea

internal class ExampleEditor(
    code: Flow<String>
) : JPanel(), SwingFlow {
    val textArea = JTextArea()

    init {
        add(textArea)

        bindings {
            code invokes textArea::setText
        }
    }
}