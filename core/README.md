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

### Usage

Refer to the [Wiki](https://github.com/eyrond/paperkt/wiki/core) for further information on how to use the components of
this module.

