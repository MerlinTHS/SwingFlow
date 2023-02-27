package io.mths.swing.flow.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

internal suspend fun yieldMainThread() {
    withContext(Dispatchers.Main) {
        yield()
    }
}