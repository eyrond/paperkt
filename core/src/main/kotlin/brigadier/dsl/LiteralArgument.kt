package dev.eyrond.paperkt.brigadier.dsl

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.eyrond.paperkt.plugin.IKotlinPlugin

typealias BukkitLiteralArgumentBuilder = LiteralArgumentBuilder<BukkitBrigadierCommandSource>

/**
 * Represents a literal brigadier argument.
 * @property plugin The plugin that owns this argument.
 */
@BrigadierDSLMarker
class LiteralArgument(
    plugin: IKotlinPlugin,
    builder: BukkitLiteralArgumentBuilder
) : Argument<BukkitLiteralArgumentBuilder>(plugin, builder)
