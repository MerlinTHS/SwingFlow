<h1 align="center">SwingFlow</h1>

Makes binding *Kotlin Flows* to *Java Swing* components easier!

To bind a simple ```String``` Flow to the text property of a ```JLabel```
we need to collect the latest value. Unfortunately, collecting a flow suspends execution.
So we have to launch a coroutine as long as we want to update the label. For example as long it's attached to a parent
or as long it's visible.

SwingFlow allows *flow-to-method* binding in a declarative manner with the ability to specify a custom lifecycle.

```kotlin
val greeting = flowOf("Hello SwingFlow!")

val header = JLabel() binds {
  greeting [::setText]
}
```

If you don't specify any lifecycle when calling ```binds```, the ```VisibilityLifecycle``` is used.
Lifecycle and binding-logic are now decoupled from each other. So when changing the lifecycle later on,
there's only this one place to consider.
This way, a Swing component is not responsible for things related to the bindings lifecycle management.

## Lifecycle

Predefined lifecycles
- ```ParentLifecycle```
- ```VisibilityLifecycle``` ( since 1.0.4 )
