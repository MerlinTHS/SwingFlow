package com.github.merlinths.swing.flow

import com.github.merlinths.swing.flow.binding.invoke
import com.github.merlinths.swing.flow.binding.shorthand.binds
import kotlinx.coroutines.flow.*
import javax.swing.JComponent
import javax.swing.JPanel

fun Flow<JComponent>.observeInPanel(): JComponent =
    JPanel() binds {
        ::setContent { this@observeInPanel }
    }

private fun JComponent.setContent(newContent: JComponent) {
    removeAll()
    add(newContent)
}