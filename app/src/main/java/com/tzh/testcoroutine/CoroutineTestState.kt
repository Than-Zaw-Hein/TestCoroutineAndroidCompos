package com.tzh.testcoroutine

data class CoroutineTestState(
    val dispatcherName: String = "",
    val isRunning: Boolean = false,
    val isLoading: Boolean = false,
    val progress: Float = 0f,
    val elapsedTime: Long = 0L
)