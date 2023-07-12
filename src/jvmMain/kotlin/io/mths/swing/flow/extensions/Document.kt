package io.mths.swing.flow.extensions

import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.annotations.TestOnly
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.Document
import kotlin.reflect.KFunction0
import kotlin.reflect.KProperty

operator fun KProperty<Document>.invoke() {

}

operator fun KFunction0<Document>.invoke(
    supplyObservable: () -> MutableStateFlow<String>
) {
    val observable = supplyObservable()

    invoke().onChange {
        observable.value = it
    }
}

fun Document.onChange(
    action: (String) -> Unit
) {
    addDocumentListener(
        object : DocumentListener {
            override fun changedUpdate(e: DocumentEvent) {
                action(text)
            }

            override fun insertUpdate(e: DocumentEvent) {
                action(text)
            }

            override fun removeUpdate(e: DocumentEvent) {
                action(text)
            }
        }
    )
}

@get:TestOnly
internal val Document.text: String
    get() = getText(0, length)