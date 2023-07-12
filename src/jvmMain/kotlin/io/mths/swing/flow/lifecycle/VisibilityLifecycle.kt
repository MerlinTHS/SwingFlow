package io.mths.swing.flow.lifecycle

import io.mths.swing.flow.binding.invoke
import io.mths.swing.flow.binding.shorthand.bind
import io.mths.swing.flow.binding.shorthand.binds
import io.mths.swing.flow.SwingFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.event.AncestorEvent
import javax.swing.event.AncestorListener
import kotlin.random.Random

class MyDumbPanel : JPanel(), SwingFlow {
    val header = JLabel()
    val refreshButton = JButton("Refresh")
    
    init {
        layout = GridLayout(0, 1)
        add(header)
        add(refreshButton)

        header.horizontalAlignment = JLabel.CENTER
    }
}

class MyPanel(
    headline: Flow<String>,
    color: Flow<Color>,
    private val onRefresh: () -> Unit
) : JPanel(), SwingFlow {
    private val header = JLabel("")
    private val refreshButton = JButton("Refresh")

    init {
        setupUI()

        bind {
            header::setText { headline }
            header::setForeground { color }
        }
    }

    private fun setupUI() {
        layout = GridLayout(0, 1)
        add(header)
        add(refreshButton)

        header.horizontalAlignment = JLabel.CENTER

        refreshButton.addActionListener {
            onRefresh()
        }
    }
}

val currentColor = MutableStateFlow(Color.BLACK)
val currentHeadline = flow {
    while (true) {
        delay(1000)
        emit("Article Nr. ${Random.nextInt(0, 100)}")
    }
}

fun mainTwo() {
    SwingUtilities.invokeLater {
        val window = JFrame("Visibility Lifecycle Example")
        val myPanel = MyDumbPanel() binds {
            header::setForeground { currentColor }
            header::setText { currentHeadline }
        }

        myPanel.refreshButton.addActionListener {
            currentColor.value =
                if (currentColor.value == Color.BLACK) {
                    Color.BLUE
                } else {
                    Color.BLACK
                }
        }

        window.contentPane.add(myPanel)
        window.setSize(380, 200)
        window.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        window.isVisible = true
    }
}
fun main() {
    SwingUtilities.invokeLater {
        val window = JFrame("Visibility Lifecycle Example")
        val myPanel = MyPanel(
            headline = currentHeadline,
            color = currentColor,
            onRefresh = {
                currentColor.value =
                    if (currentColor.value == Color.BLACK) {
                        Color.BLUE
                    } else {
                        Color.BLACK
                    }
            }
        )

        window.contentPane.add(myPanel)
        window.setSize(380, 200)
        window.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        window.isVisible = true
    }
}

class VisibilityLifecycle<Type: JComponent> : Lifecycle<Type> {
    private var isBound = false

    context (JComponent)
    override fun register(
        onBind: Runnable,
        onUnbind: Runnable
    ) {
        addAncestorListener(
            object : AncestorListener {
                override fun ancestorAdded(event: AncestorEvent?) {
                    handleVisibilityChange(onBind, onUnbind)
                }

                override fun ancestorMoved(event: AncestorEvent?) {
                    handleVisibilityChange(onBind, onUnbind)
                }

                override fun ancestorRemoved(event: AncestorEvent?) {
                    onUnbind.run()
                }
            }
        )
    }

    context (JComponent)
    private fun handleVisibilityChange(
        onBind: Runnable,
        onUnbind: Runnable
    ) {
        if (isVisible) {
            bind(onBind)
        } else {
            unbind(onUnbind)
        }
    }

    private fun bind(onBind: Runnable) {
        if (!isBound) {
            onBind.run()
        }
    }

    private fun unbind(onUnbind: Runnable) {
        if (isBound) {
            onUnbind.run()
        }
    }
}