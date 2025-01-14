package com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CLibCircularProgressBar(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    trackColor: Color = Color(0xFFE92EB0)

) {
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite value progress")

    val progressValue by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 5f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "Progress Value"
    )

    Canvas(modifier = modifier.size(size)) {
        drawArc(
            color = Color.Black.copy(alpha = 0.08f),
            startAngle = 90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(
                width = 6.dp.value,
            )
        )
        drawArc(
            color = trackColor,
            startAngle = -90f * progressValue,
            useCenter = false,
            sweepAngle = 90f,
            style = Stroke(
                width = 6.dp.value, cap = StrokeCap.Round
            )

        )
    }
}

@Preview(name = "Progress Bar Preview")
@Composable
private fun CircularProgressPrev() {
    Box(modifier = Modifier.padding(16.dp)) {
        CLibCircularProgressBar()
    }
}