@file:Suppress("unused")

package dev.eyrond.paperkt.brigadier.dsl

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.eyrond.paperkt.plugin.IKotlinPlugin
import org.bukkit.permissions.Permission

typealias BukkitLiteralArgumentBuilder = LiteralArgumentBuilder<BukkitBrigadierCommandSource>

/**
 * Represents a literal brigadier argument.
 * @property plugin The plugin that owns this argument.
 */
@BrigadierDSLMarker
class LiteralArgument(
    plugin: IKotlinPlugin,
    builder: BukkitLiteralArgumentBuilder
) : Argument<BukkitLiteralArgumentBuilder>(plugin, builder) {

    /**
     * Sets a permission requirement for this argument.
     */
    @BrigadierDSLMarker
    fun requiresPermission(permission: Permission) = apply {
        builder.requires { it.bukkitSender.hasPermission(permission) }
    }

    /**
     * Sets a permission requirement for this argument.
     */
    @BrigadierDSLMarker
    fun requiresPermission(permission: String) = apply {
        builder.requires { it.bukkitSender.hasPermission(permission) }
    }
}
