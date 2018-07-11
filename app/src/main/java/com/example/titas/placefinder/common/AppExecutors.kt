package com.example.titas.placefinder.common

import java.util.concurrent.Executors

/**
 * Created by Titas on 7/12/2018.
 */

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 * Utility method to run blocks on a dedicated background thread, used for io/database work.
 */
fun runOnIoThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}
