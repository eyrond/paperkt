package dev.eyrond.paperkt.plugin

import kotlinx.coroutines.CoroutineScope
import mu.KLogger
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

/**
 * Interface for Kotlin plugins for flexible interoperability with other Kotlin plugins.
 */
interface IKotlinPlugin : Plugin {

    /**
     * Coroutine context of your plugin.
     * Coroutines within this context will be executed in the main server thread.
     * Can be used only when your plugin is enabled.
     * All coroutines within this context will be cancelled on stopping.
     */
    val coroutineContext: CoroutineContext

    /**
     * Coroutine scope of your plugin.
     * Coroutines within this scope will be executed in the main server thread.
     * Can be used only when your plugin is enabled.
     * All coroutines within this scope will be cancelled on stopping.
     */
    val coroutineScope: CoroutineScope

    /**
     * Async coroutine context of your plugin.
     * Coroutines within this context will always be executed asynchronously.
     * Can be used only when your plugin is enabled.
     * All coroutines within this context will be cancelled on stopping.
     */
    val asyncCoroutineContext: CoroutineContext

    /**
     * Async coroutine scope of your plugin.
     * Coroutines within this scope will always be executed asynchronously.
     * Can be used only when your plugin is enabled.
     * All coroutines within this scope will be cancelled on stopping.
     */
    val asyncCoroutineScope: CoroutineScope

    /**
     * Kotlin logger for your plugin.
     */
    val log: KLogger

    /**
     * This method is called once your plugin is loaded.
     * Keep in mind that the coroutine contexts and scopes are not available yet, as your plugin is still disabled at this point.
     */
    suspend fun onLoaded() {}

    /**
     * This method is called before [onEnabled] to load necessary configuration.
     * May be called again to reload the configuration.
     */
    suspend fun loadConfig()

    /**
     * This method is called when the plugin is enabled.
     */
    suspend fun onEnabled()

    /**
     * This method is called when the plugin is disabled.
     */
    suspend fun onDisabled()
}
