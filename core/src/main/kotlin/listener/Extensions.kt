@file:Suppress("unused")

package dev.eyrond.paperkt.listener

import dev.eyrond.paperkt.plugin.IKotlinPlugin
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

/**
 * Registers the [listener] to this Kotlin plugin.
 */
fun IKotlinPlugin.register(listener: KotlinListener): KotlinListener {
    return listener.register(this)
}

/**
 * Creates a [KotlinListener] from the given [listener] function and registers to this Kotlin plugin.
 */
inline fun <reified T : Event> IKotlinPlugin.listener(
    ignoreCancelled: Boolean = false,
    crossinline listener: suspend T.() -> Unit
): KotlinListener {
    return if (ignoreCancelled) {
        object : KotlinListener {
            @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
            suspend fun onEvent(event: T) = listener(event)
        }
    } else {
        object : KotlinListener {
            @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
            suspend fun onEvent(event: T) = listener(event)
        }
    }.register(this)
}
