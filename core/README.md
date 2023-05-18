# PaperKt Core

This is the core module of PaperKt that contains only the core components required for plugin development using Kotlin.
You can bundle it into your plugins, or depend on the [Library](../library) plugin which already bundles it.

## Quick Start

First of all, add the core to your project dependencies.

### Bundle with your plugin

If you want to bundle the core with your plugin:

```kotlin
implementation("dev.eyrond.paperkt:core:VERSION")
```

### Depend on the [Library](../library) plugin

Or if you want to depend on the [Library](../library) plugin:

```kotlin
compileOnly("dev.eyrond.paperkt:core:VERSION")
```

And add the dependency to your plugin.yml:

```yaml
depend: [ PaperKt ]
```

## Components Overview

### KotlinPlugin

This is an abstraction over the JavaPlugin class that brings in support for coroutines.

For coroutines, you can respectively use the `coroutineScope` and `asyncCoroutineScope` properties of the `KotlinPlugin`
class. Also, it has a `log` property that you can use to log messages using Kotlin style logger.

```kotlin
class YourPlugin : KotlinPlugin() {

    override suspend fun loadConfig() {
        // Here you can load configuration for your plugin.
        // This method can be called multiple times during plugin lifecycle.
    }

    override suspend fun onEnabled() {
        // Here should be initialization logic for your plugin.
        // This method is called once when plugin is enabled.
    }

    override suspend fun onDisabled() {
        // Here should be cleanup logic for your plugin.
        // This method is called once when plugin is disabled.
    }
}
```

### KotlinListener

This is an alternative for the Listener interface that brings in support for coroutines.

To register a listener you can use the default `register` method of the interface.

Implementation variant:

```kotlin
class YourListener : KotlinListener {

    @EventHandler
    suspend fun onPlayerJoin(event: PlayerJoinEvent) {
        // Here should be logic for player join event.
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {
        // Here should be logic for player leave event.
    }
}
```

DSL variant:

```kotlin
override suspend fun onEnabled() {
    listener<PlayerJoinEvent> {
        // Here should be logic for player join event.
    }
}
```

### KotlinCommand

This is an abstraction over the Command class that brings in support for coroutines.

To register a command you can use the `register` method of the class. Respectively you can unregister the command using
the `unregister` method.

Implementation variant:

```kotlin
class YourCommand(plugin: IKotlinPlugin) : KotlinCommand(plugin, name = "mycommand") {

    override suspend fun execute(sender: CommandSender, alias: String, args: List<String>): Boolean {
        // Here should be execution logic of your command.
        return true
    }
}
```

DSL variant:

```kotlin
override suspend fun onEnabled() {
    command("mycommand") { sender, alias, args ->
        // Here should be execution logic of your command.
        return@command true
    }
}
```


