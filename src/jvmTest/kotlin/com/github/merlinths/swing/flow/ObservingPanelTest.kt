package com.github.merlinths.swing.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.amshove.kluent.*
import javax.swing.JPanel
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ObservingPanelTest {
    @Test
    fun `Constant content`() = runTest {
        val content = JPanel()
        val panel = flowOf(content).asObservingPanel()

        yieldMainThread()

        with(panel) {
            componentCount `should be equal to`  1
            components.first() `should be` content
        }
    }

    @Test
    fun `Changing content`() = runTest {
        val testCompleted = Channel<Boolean>(capacity = 1)

        val job = launch {
            val initialContent = JPanel()
            val nextContent = JPanel()

            val currentContent = MutableStateFlow(initialContent)
            val panel = currentContent.asObservingPanel()

            yieldMainThread()

            with(panel) {
                components.shouldNotBeEmpty()
                components.first() `should be` initialContent

                currentContent.value = nextContent
                componentCount `should be equal to` 1
                components.first()`should be` nextContent
            }

            testCompleted.send(true)
        }

        testCompleted.receive()
        job.cancel()
    }

    private suspend fun yieldMainThread() {
        withContext(Dispatchers.Main) {
            yield()
        }
    }
}