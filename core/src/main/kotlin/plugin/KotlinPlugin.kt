package dev.eyrond.paperkt.plugin

import dev.eyrond.paperkt.brigadier.Brigadier
import dev.eyrond.paperkt.brigadier.BukkitBrigadier
import dev.eyrond.paperkt.coroutines.AsyncPluginCoroutineDispatcher
import dev.eyrond.paperkt.coroutines.PluginCoroutineDispatcher
import dev.eyrond.paperkt.coroutines.PluginCoroutineSynchronizer
import kotlinx.coroutines.*
import mu.KLogger
import mu.toKLogger
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

/**
 * Represents a Kotlin plugin and its main class.
 * This is an abstraction on top of [JavaPlugin] class for Kotlin plugins.
 */
@Suppress("unused")
abstract class KotlinPlugin : JavaPlugin(), IKotlinPlugin {

    private val synchronizer by lazy { PluginCoroutineSynchronizer(this) }
    private val dispatcher by lazy { PluginCoroutineDispatcher(synchronizer) }
    final override val coroutineContext: CoroutineContext by lazy { createContext("plugin", dispatcher) }
    final override val coroutineScope: CoroutineScope by lazy { CoroutineScope(coroutineContext) }
    private val asyncDispatcher by lazy { AsyncPluginCoroutineDispatcher(synchronizer) }
    final override val asyncCoroutineContext: CoroutineContext by lazy { createContext("async", asyncDispatcher) }
    final override val asyncCoroutineScope: CoroutineScope by lazy { CoroutineScope(asyncCoroutineContext) }
    final override val log: KLogger = slF4JLogger.toKLogger()
    final override val brigadier: Brigadier by lazy { BukkitBrigadier(this) }

    final override fun reloadConfig() {
        synchronizer.run { loadConfig() }
    }

    final override fun onLoad() {
        coroutineScope.ensureActive()
        asyncCoroutineScope.ensureActive()
        runBlocking { onLoaded() }
    }

    final override fun onEnable() {
        synchronizer.run {
            loadConfig()
            onEnabled()
        }
    }

    final override fun onDisable() {
        try {
            runBlocking { onDisabled() }
        } finally {
            val cancellationException = CancellationException("Stopping the plugin.")
            dispatcher.cancelChildren(cancellationException)
            asyncDispatcher.cancelChildren(cancellationException)
        }
    }

    final override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): Boolean {
        error(
            """
                The onCommand method should not be used with Kotlin plugins.
                Please, follow to the docs for further information.
            """.trimIndent()
        )
    }

    final override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        error(
            """
                The onTabComplete method should not be used with Kotlin plugins.
                Please, follow to the docs for further information.
            """.trimIndent()
        )
    }

    private fun createContext(name: String, dispatcher: CoroutineDispatcher): CoroutineContext {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            log.warn(throwable) { "An exception has occurred in a coroutine within the $name context." }
        }
        return exceptionHandler + SupervisorJob() + dispatcher
    }
}
