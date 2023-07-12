package io.mths.swing.flow

import io.mths.swing.flow.binding.get
import io.mths.swing.flow.binding.invoke
import io.mths.swing.flow.binding.shorthand.binds
import io.mths.swing.flow.extensions.invoke
import io.mths.swing.flow.extensions.onChange
import io.mths.swing.flow.util.withAttached
import io.mths.swing.flow.util.yieldMainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should be equal to`
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.event.UndoableEditEvent
import javax.swing.event.UndoableEditListener
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SwingFlowTest {
    @Test
    fun experimentWithBindings() = runBlocking {
        val text = MutableStateFlow("")

        val textField = JTextField().apply {
            ::text::
            set { text }

            ::document::
            get { text }
        }

        launch {
            repeat(3) {
                delay(1000)
                textField.text += "Input"
            }
        }

        text.take(3).collectLatest {
            println(it)
        }
        assert(true)
    }

    @Test
    fun `JLabel text changes`() = runTest {
        val code = MutableStateFlow("Hello")
        val label = JLabel() binds {
            ::text::
            set { code }
        }

        withAttached(label) {
            yieldMainThread()
            text `should be equal to` "Hello"

            code.value = "Bye"
            yieldMainThread()
            text `should be equal to` "Bye"
        }
    }
}