package com.github.merlinths.swing.flow

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
    fun `Constant content`() = runTest {
        val content = JPanel()
        val panel = flowOf(content).observeAsPanel()

        panel.attachedScope {
            yieldMainThread()

            with(panel) {
                componentCount `should be equal to`  1
                components.first() `should be` content
            }
        }
    }

    @Test
    fun `Changing content`() = runTest {
        val initialContent = JPanel()
        val nextContent = JLabel()

        val currentContent = MutableStateFlow<JComponent>(initialContent)
        val panel = currentContent.observeAsPanel()

        panel.attachedScope {
            yieldMainThread()

            components.shouldNotBeEmpty()
            components.first() `should be` initialContent


            currentContent.value = nextContent
            yieldMainThread()

            componentCount `should be equal to` 1
            components.first()`should be` nextContent
        }
    }
}