package com.github.merlinths.swing.flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should be equal to`
import kotlin.test.Test

class SwingFlowTest {
    @Test
    fun `TextArea text changes`() = runTest {
        val code = MutableStateFlow("Hello")
        val editor = ExampleEditor(code)

        editor.attachedScope {
            yieldMainThread()
            editor.textArea.text `should be equal to` "Hello"

            code.value = "Bye"
            yieldMainThread()
            editor.textArea.text `should be equal to` "Bye"
        }
    }
}