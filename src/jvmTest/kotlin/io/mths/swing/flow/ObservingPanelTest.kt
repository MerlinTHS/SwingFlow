package io.mths.swing.flow

import io.mths.swing.flow.util.withAttached
import io.mths.swing.flow.util.yieldMainThread
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.amshove.kluent.*
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import kotlin.test.Test
@OptIn(ExperimentalCoroutinesApi::class)
class ObservingPanelTest {

    @Test
    fun `Initial content is set`() = runTest {
        val content = JPanel()
        val panel = flowOf(content).observeInPanel()

        withAttached(panel) {
            yieldMainThread()

            componentCount `should be equal to` 1
            components.first() `should be` content
        }
    }

    @Test
    fun `Content changes`() = runTest {
        val initialContent = JPanel()
        val nextContent = JLabel()

        val currentContent = MutableStateFlow<JComponent>(initialContent)
        val panel = currentContent.observeInPanel()

        withAttached(panel) {
            yieldMainThread()

            components.shouldNotBeEmpty()
            components.first() `should be` initialContent

            currentContent.value = nextContent
            yieldMainThread()

            componentCount `should be equal to` 1
            components.first() `should be` nextContent
        }
    }
}