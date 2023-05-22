@file:Suppress("unused")

package dev.eyrond.paperkt.brigadier.dsl

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.eyrond.paperkt.plugin.IKotlinPlugin
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import java.util.concurrent.CompletableFuture

typealias BukkitRequiredArgumentBuilder<T> = RequiredArgumentBuilder<BukkitBrigadierCommandSource, T>

/**
 * Represents a required brigadier argument.
 * @property plugin The plugin that owns this argument.
 */
@BrigadierDSLMarker
class RequiredArgument<T>(
    plugin: IKotlinPlugin,
    builder: BukkitRequiredArgumentBuilder<T>
) : Argument<BukkitRequiredArgumentBuilder<T>>(plugin, builder) {

    /**
     * Sets the suggestion provider for this argument with suspend support.
     */
    @BrigadierDSLMarker
    inline fun suggestsSuspend(crossinline provider: suspend BukkitCommandContext.(SuggestionsBuilder) -> Suggestions) {
        suggests { context, builder ->
            plugin.asyncCoroutineScope.async { context.provider(builder) }.asCompletableFuture()
        }
    }

    /**
     * Sets the suggestion provider for this argument.
     */
    @BrigadierDSLMarker
    inline fun suggests(crossinline provider: BukkitCommandContext.(SuggestionsBuilder) -> CompletableFuture<Suggestions>) {
        suggests { context, builder -> context.provider(builder) }
    }

    /**
     * Sets the suggestion provider for this argument.
     */
    @BrigadierDSLMarker
    fun suggests(provider: SuggestionProvider<BukkitBrigadierCommandSource>) {
        builder.suggests(provider)
    }
}
