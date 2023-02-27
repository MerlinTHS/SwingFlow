package io.mths.swing.flow

import io.mths.swing.flow.binding.invoke
import io.mths.swing.flow.binding.shorthand.binds
import io.mths.swing.flow.util.withAttached
import io.mths.swing.flow.util.yieldMainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should be equal to`
import javax.swing.JLabel
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SwingFlowTest {
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