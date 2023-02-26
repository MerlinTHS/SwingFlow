<h1 align="center">SwingFlow</h1>

Makes binding *Kotlin Flows* to *Java Swing* components easier!

To bind a simple ```String``` Flow to the _text_ property of a ```JLabel```
we need to collect the latest value. Unfortunately, collecting a flow suspends execution.
So we have to launch a coroutine as long as we want to update the label. For example as long it's attached to a parent
or as long it's visible.

SwingFlow allows to bind your observable data in a syntax, which
looks like the normal (non-reactive) calls to set the desired properties
with the ability to specify a custom lifecycle.

```kotlin
val greeting = flowOf("Hello SwingFlow!")

val header = JLabel() binds {
    ::setText { greeting }
}
```

Calling methods this way, results in a one-directional binding.
To bind a property ( typically bi-directional ).

```kotlin
val greeting = JLabel() binds {
    ::text { greeting }
}
```

There is also a way to bind the property in a one-directional manner.

```kotlin
val greeting = JLabel() binds {
    ::text::
    set { greeting }
}
```

You can use all the intermediate flow operations inside these bindings.

```kotlin
val countDown = (10 downTo 0)
    .asFlow()
    .onEach { delay(1000) }

val number = JLabel() binds {
    ::text::
    set {
        countDown.map(Int::toString)
    }
}
```

If you don't specify any lifecycle when calling ```binds```, the ```ParentLifecycle``` is used.
Lifecycle and binding-logic are now decoupled from each other. So when changing the lifecycle later on,
there's only this one place to consider.
This way, a Swing component is not responsible for things related to the bindings lifecycle management.

## Lifecycles

Predefined lifecycles
- ```ParentLifecycle```