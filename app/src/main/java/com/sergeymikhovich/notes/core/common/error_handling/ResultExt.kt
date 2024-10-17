package com.sergeymikhovich.notes.core.common.error_handling

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.coroutines.runSuspendCatching

typealias UnitResult = Result<Unit, Throwable>

inline fun runSuspendCatchingUnit(block: () -> Unit): UnitResult = runSuspendCatching(block)