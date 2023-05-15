package dev.eyrond.paperkt

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

val unixTimestampNow: Long get() = System.currentTimeMillis() / 1000

val Int.ticks: Duration get() = (this * 50).milliseconds

val Long.ticks: Duration get() = (this * 50).milliseconds
