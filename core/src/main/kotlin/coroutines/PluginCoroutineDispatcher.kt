package dev.eyrond.paperkt.coroutines

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

internal class PluginCoroutineDispatcher(
    private val synchronizer: PluginCoroutineSynchronizer
) : CoroutineDispatcher(), Delay {

    private val plugin = synchronizer.plugin
    private val server get() = plugin.server

    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        val task = server.scheduler.runTaskLater(
            plugin,
            Runnable { continuation.apply { resumeUndispatched(Unit) } },
            timeMillis / 50
        )
        continuation.invokeOnCancellation { task.cancel() }
    }

    override fun isDispatchNeeded(context: CoroutineContext): Boolean {
        synchronizer.unblockIfNeeded()
        return !plugin.server.isPrimaryThread
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!plugin.isEnabled) return
        server.scheduler.runTask(plugin, block)
    }
}
