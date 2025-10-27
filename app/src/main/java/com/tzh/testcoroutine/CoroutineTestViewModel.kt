package com.tzh.testcoroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class CoroutineTestViewModel : ViewModel() {

    private val _state = MutableStateFlow<List<CoroutineTestState>>(emptyList())
    val state = _state.asStateFlow()

    private var runningJobs = mutableMapOf<String, Job>()

    private val dispatchers = listOf(
        "IO" to Dispatchers.IO,
        "Default" to Dispatchers.Default,
        "Main" to Dispatchers.Main,
        "Unconfined" to Dispatchers.Unconfined
    )

    init {
        _state.value = dispatchers.map { CoroutineTestState(it.first) }
    }

    fun runTest(dispatcherName: String) {
        // Cancel if already running
        runningJobs[dispatcherName]?.cancel()

        val dispatcher = dispatchers.first { it.first == dispatcherName }.second

        val job = viewModelScope.launch(dispatcher) {
            val index = _state.value.indexOfFirst { it.dispatcherName == dispatcherName }
            updateState(index) {
                it.copy(isLoading = true)
            }
            delay(500)
            var progress = 0f
            var elapsed = 0L
            val time = measureTimeMillis {
                for (i in 1..100) {
                    val dataCalculation = (i..10000000).fold(initial = 1) { acc,t ->
                        acc + t
                    }
                    Log.d("CoroutineTestViewModel", "dataCalculation: $dataCalculation")
                    progress = i / 100f
                    updateState(index) {
                        it.copy(isRunning = true, progress = progress)
                    }
                }
            }
            elapsed = time

            updateState(index) {
                it.copy(isRunning = false, elapsedTime = elapsed, progress = 1f, isLoading = false)
            }
        }

        runningJobs[dispatcherName] = job
    }

    private fun updateState(index: Int, transform: (CoroutineTestState) -> CoroutineTestState) {
        val currentList = _state.value.toMutableList()
        currentList[index] = transform(currentList[index])
        _state.value = currentList
    }

    fun cancelAll() {
        runningJobs.values.forEach { it.cancel() }
        runningJobs.clear()
        _state.value = dispatchers.map { CoroutineTestState(it.first) }
    }
}