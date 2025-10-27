package com.tzh.testcoroutine.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tzh.testcoroutine.CoroutineTestState
import com.tzh.testcoroutine.CoroutineTestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoroutineTestScreen(
    viewModel: CoroutineTestViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coroutine Dispatcher Test") },
                actions = {
                    TextButton(onClick = { viewModel.cancelAll() }) {
                        Text("Reset", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(span = {
                GridItemSpan(maxLineSpan)
            }) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "ExcutorService",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            """
                            var progress = 0f
                            var elapsed = 0L
                            val time = measureTimeMillis {
                                for (i in 1..100) {
                                    val dataCalculation = (i..10000000).fold(initial = 1) { acc,t ->
                                        acc + t
                                    }
                                    Log.d("CoroutineTestViewModel", "dataCalculation: $\dataCalculation")
                                    progress = i / 100f
                                    updateState(index) {
                                        it.copy(isRunning = true, progress = progress)
                                    }
                                }
                            }
                            elapsed = time  
                            """.trimIndent()
                        )
                        Text(
                            "Dispatcher", style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                            )
                        )
                    }
                }
            }
            items(state) { item ->
                DispatcherCard(
                    state = item,
                    onStart = { viewModel.runTest(item.dispatcherName) }
                )
            }
        }
    }
}

@Composable
private fun DispatcherCard(
    state: CoroutineTestState,
    onStart: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = state.dispatcherName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                if (state.isLoading) {
                    CircularProgressIndicator()
                }
            }

            LinearProgressIndicator(
                progress = { state.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .progressSemantics(state.progress)
            )

            Text(
                text = if (state.isRunning)
                    "Running... ${(state.progress * 100).toInt()}%"
                else if (state.elapsedTime > 0)
                    "Completed in ${state.elapsedTime} ms"
                else
                    "Idle",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = onStart,
                enabled = !state.isRunning,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.isRunning) "Running..." else "Start Test")
            }
        }
    }
}