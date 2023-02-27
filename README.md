<h1 align="center">SwingFlow</h1>

<div>

[![Maven Central](https://img.shields.io/maven-central/v/io.github.merlinths/swing-flow?color=blue)]()
[![Apache license](https://img.shields.io/badge/license-Apache%20License%202.0-red.svg)](https://www.apache.org/licenses/LICENSE-2.0)

</div>

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
val header = JLabel() binds {
    ::text { greeting }
}
```

There is also a way to bind the property in a one-directional manner
( the recommended _Section - Syntax_ ). Currently only ```set``` is supported.

```kotlin
val header = JLabel() binds {
    ::text::
    set { greeting }
}
```

You can use all the intermediate flow operations inside these bindings.

```kotlin
val countDown =
    (10 downTo 0)
    .asFlow()
    .onEach { delay(1000) }

val number = JLabel() binds {
    ::text::
    set {
        countDown.map(Int::toString)
    }
}
```


As soon as ```get``` is supported, one can do the same in the other direction as well.

```kotlin
val name = MutableStateFlow("SwingFlow")

val header = JLabel() binds {
    ::text::
    set {
        name.map { "Hello $it!" }
    }
}

val yourName = JTextField() binds {
    ::text::
    get { name }
}
```

If you don't specify any lifecycle when calling ```binds```, the ```ParentLifecycle``` is used.
Lifecycle and binding-logic are now decoupled from each other. So when changing the lifecycle later on,
there's only this one place to consider.
This way, a Swing component is not responsible for things related to the bindings lifecycle management.

## Lifecycles

Predefined lifecycles
- ```ParentLifecycle```