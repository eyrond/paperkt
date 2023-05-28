@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package dev.eyrond.paperkt.command

import dev.eyrond.paperkt.plugin.IKotlinPlugin
import kotlinx.coroutines.launch
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.SimpleCommandMap
import org.bukkit.entity.Player

/**
 * Kotlin's abstraction on top of the [Command] class with coroutine support.
 * @param plugin The plugin that owns this command.
 * @param name The name of the command.
 * @param description The description of the command.
 * @param usageMessage The usage message of the command.
 * @param aliases The aliases of the command.
 * @see dev.eyrond.paperkt.command.command
 */
abstract class KotlinCommand(
    protected val plugin: IKotlinPlugin,
    name: String,
    description: String = "",
    usageMessage: String = "/$name",
    aliases: List<String> = emptyList(),
) : Command(name, description, usageMessage, aliases) {

    protected val server get() = plugin.server

    final override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        var isSuccessful = true
        plugin.coroutineScope.launch {
            isSuccessful = execute(sender, commandLabel, args.toList())
        }
        return isSuccessful
    }

    /**
     * Executes the command, returning its success.
     * @param sender Source of the command.
     * @param alias Alias of the command which was used.
     * @param args Passed command arguments.
     * @return true if the command was successful, otherwise false.
     */
    abstract suspend fun execute(sender: CommandSender, alias: String, args: List<String>): Boolean

    final override fun tabComplete(
        sender: CommandSender,
        alias: String,
        args: Array<String>
    ): List<String> {
        val argsList = args.toList()
        var tabComplete: List<String>? = null
        plugin.coroutineScope.launch {
            tabComplete = tabComplete(sender, alias, argsList)
        }
        return tabComplete ?: defaultTabComplete(sender, argsList)
    }

    /**
     * Requests a list of possible completions for a command argument.
     * @param sender Source of the command.
     * @param alias Alias of the command which was used.
     * @param args The arguments passed to the command, including final partial argument to be completed and command label.
     * @return List of possible completions for the final argument.
     */
    protected open suspend fun tabComplete(
        sender: CommandSender,
        alias: String,
        args: List<String>,
    ): List<String> = defaultTabComplete(sender, args)

    final override fun tabComplete(
        sender: CommandSender,
        alias: String,
        args: Array<String>,
        location: Location?
    ): List<String> {
        val argsList = args.toList()
        var tabComplete: List<String>? = null
        plugin.coroutineScope.launch {
            tabComplete = tabComplete(sender, alias, argsList, location)
        }
        return tabComplete ?: defaultTabComplete(sender, argsList)
    }

    protected open suspend fun tabComplete(
        sender: CommandSender,
        alias: String,
        args: List<String>,
        location: Location?
    ): List<String> = tabComplete(sender, alias, args)

    private fun defaultTabComplete(sender: CommandSender, args: List<String>): List<String> {
        val server = sender.server
        if (args.isEmpty() || !server.suggestPlayerNamesWhenNullTabCompletions()) return emptyList()
        val lastWord = args.last()
        val senderPlayer = sender as? Player
        return server.onlinePlayers.filter { player ->
            val isPlayerAcknowledgedBySender = senderPlayer == null || senderPlayer.canSee(player)
            isPlayerAcknowledgedBySender && player.name.startsWith(lastWord, ignoreCase = true)
        }.map { it.name }.sortedWith(String.CASE_INSENSITIVE_ORDER)
    }

    /**
     * Registers this command to the server.
     * @return true if the command was successfully registered, otherwise false.
     */
    fun register(): Boolean {
        val isSuccessful = server.commandMap.register(plugin.name, this)
        if (isSuccessful) {
            updateCommandsForPlayers()
        }
        return isSuccessful
    }

    /**
     * Unregisters this command from the server.
     * @return true if the command was successfully unregistered, otherwise false.
     */
    fun unregister(): Boolean {
        val simpleCommandMap = server.commandMap as? SimpleCommandMap ?: return false
        val knownCommands = simpleCommandMap.knownCommands
        val keysForRemoval = knownCommands.filterValues { it === this }.keys
        val result = knownCommands.keys.removeAll(keysForRemoval)
        updateCommandsForPlayers()
        return result
    }

    protected fun updateCommandsForPlayers() {
        server.onlinePlayers.forEach { it.updateCommands() }
    }
}
