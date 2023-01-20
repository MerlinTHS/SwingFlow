package com.github.merlinths.swing.flow

import com.github.merlinths.swing.flow.binding.get
import com.github.merlinths.swing.flow.binding.shorthand.binds
import kotlinx.coroutines.flow.*
import javax.swing.JComponent
import javax.swing.JPanel

fun Flow<JComponent>.observeAsPanel(): JComponent =
    JPanel() binds {
        this@observeAsPanel [::setContent]
    }

private fun JComponent.setContent(newContent: JComponent) {
    removeAll()
    add(newContent)
}