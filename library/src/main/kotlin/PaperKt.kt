package dev.eyrond.paperkt

import dev.eyrond.paperkt.common.Strings
import dev.eyrond.paperkt.plugin.KotlinPlugin

@Suppress("UnstableApiUsage", "unused")
internal class PaperKt : KotlinPlugin() {

    override suspend fun loadConfig() {
        // There is no configuration in this library plugin.
    }

    override suspend fun onEnabled() {
        log.info { Strings.formattedBanner(pluginMeta.version) }
    }

    override suspend fun onDisabled() {
        // There is no disable logic in this library plugin.
    }
}
