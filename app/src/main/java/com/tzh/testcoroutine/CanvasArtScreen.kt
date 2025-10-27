package com.tzh.testcoroutine

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasArtScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "canvasAnimation")
    // Animate circle movement horizontally
    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 300f, label = "offsetX",
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )
    // Animate color
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFF2196F3), targetValue = Color (0xFFE91E63),label = "animatedColor",
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Compose Canvas Demo",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp)
            ) {

                //Background gradient
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF212121), Color(0xFF424242))
                    ),
                    size = size
                )

                val width = size.width
                val height = size.height

              // Moving circle
                drawCircle(
                    color = animatedColor,
                    radius = 40f,
                    center = Offset(offsetX + 100f, height / 2)
                )
//                // Static rectangle
                drawRect(
                    color = Color(0xFF4CAF50),
                    topLeft = Offset(width / 2 - 100f, height / 2 - 100f),
                    size = Size(200f, 200f),
                    style = Stroke(width = 4f)
                )
                // Diagonal line
                drawLine(
                    color = Color.Yellow,
                    start = Offset(0f, 0f),
                    end = Offset(width, height),
                    strokeWidth = 3f
                )
                // Custom Path (wave)
                val path = Path().apply {
                    moveTo(0f, height / 1.5f)
                    for (x in 0 until width.toInt() step 10) {
                        val y = (sin(x * 0.05f) * 30 + height / 1.5f).toFloat()
                        lineTo(x.toFloat(), y)
                    }
                }
                drawPath(
                    path = path,
                    color = Color.Cyan,
                    style = Stroke(width = 3f)
                )
                // Text on Canvas
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "Canvas Power 💥",
                        width / 3,
                        height - 50f,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.WHITE
                            textSize = 42f
                            textAlign = android.graphics.Paint.Align.LEFT
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
    }
}
