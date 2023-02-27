package com.github.merlinths.swing.flow.lifecycle

import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.swing.JLabel
import javax.swing.JPanel

@ExtendWith(MockKExtension::class)
class ParentLifecycleTest {
    private lateinit var parent: JPanel
    private lateinit var child: JLabel
    private lateinit var lifecycle: ParentLifecycle<JLabel>

    @BeforeEach
    fun initProperties() {
        parent = JPanel()
        child = JLabel()

        lifecycle = ParentLifecycle()
    }

    @Test
    fun `Executes onBind when getting attached`() {
        val onBind = mockk<() -> Unit>()
        every { onBind() } just Runs

        with(child) {
            lifecycle.register(onBind, onUnbind = {})
        }

        verify { onBind wasNot Called }
        parent.add(child)
        verify(exactly = 1) { onBind() }
    }

    @Test
    fun `Executes onUnbind when getting detached`() {
        val onUnbind = mockk<() -> Unit>()
        every { onUnbind() } just Runs

        with(child) {
            lifecycle.register({}, onUnbind)
        }

        verify { onUnbind wasNot Called }

        parent.add(child)
        verify { onUnbind wasNot Called }

        parent.remove(child)
        verify(exactly = 1) { onUnbind() }
    }
}