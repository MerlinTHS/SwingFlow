package com.github.merlinths.swing.flow

import com.github.merlinths.swing.flow.binding.invoke
import com.github.merlinths.swing.flow.binding.shorthand.bind
import com.github.merlinths.swing.flow.binding.shorthand.binds
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.awt.Color
import java.awt.Dimension
import java.awt.GridBagLayout
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingUtilities
import kotlin.reflect.KProperty



fun JLabel.setStatus(status: Status) {
    text = status.name
    foreground = status.color
}

enum class Status {
    Connected, Disconnected
}

fun randomStatus(): Status =
    Status.values().random()

val Status.color: Color get() =
    if (this == Status.Connected) {
        Color.GREEN
    } else {
        Color.RED
    }

fun <Type, PropertyType> Flow<Type>.map(
    property: KProperty<PropertyType>
): Flow<PropertyType> =
    map { property.call(it) }


@OptIn(DelicateCoroutinesApi::class)
object Repository {
    private val _status = MutableStateFlow(Status.Disconnected)
    val status = _status.asSharedFlow()

    init {
        GlobalScope.launch(Dispatchers.Default) {
            while (currentCoroutineContext().isActive) {
                delay(1000)
                _status.emit(randomStatus())
            }
        }
    }
}

fun main() = SwingUtilities.invokeLater {
    JFrame("SwingFlow Demo").run {
        initWindow()
        initContent()

        isVisible = true
    }
}

fun JFrame.initWindow() {
    setLocationRelativeTo(null)
    defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    size = Dimension(300, 175)
}

fun JFrame.initContent() {
    val statusLabelOne = JLabel() binds {
        with(Repository.status) {
            // Bidirectional ( currently only unidirectional support )
            /*::foreground {
                map(Status::color)
            }*/

            // Or later ( for only one direction - more explicit )
            ::foreground::
            set {
                map(Status::color)
            }

            ::text::
            set {
                map(Status::name)
            }

            /*
                Since Kotlin 1.9 you can also reference synthetic properties.

            */
        }
    }
    // Or use an extension function.
    // It looks pretty much like the original function call, but it's reactive!
    // Note that you have to use curly brackets, otherwise you will receive the following:
    // This syntax is reserved for future use; to call a reference, enclose it in parentheses: (foo::bar)(args)
    val statusLabel = JLabel() binds {
        ::setStatus { Repository.status }
    }

    layout = GridBagLayout()
    add(statusLabel)
}