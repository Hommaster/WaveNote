package com.example.wavenote.helpers

import androidx.compose.animation.core.*
import androidx.compose.animation.core.RepeatMode.Restart
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.wavenote.R
import kotlinx.coroutines.delay


@Composable
fun PulsarFab(
    onClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .padding(268.dp, 690.dp, 0.dp, 0.dp)
    ) {
        MultiplePulsarEffect { modifierEffect ->
            FloatingActionButton(
                modifier = modifierEffect,
                shape = FloatingActionButtonDefaults.largeShape,
                containerColor = colorResource(id = R.color.orange),
                onClick = { onClick() },
            ) {
                Icon(
                    modifier = Modifier
                        .size(50.dp),
                    tint = Color.White,
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun MultiplePulsarEffect(
    pulsarRadius: Float = 13f,
    pulsarColor: Color = colorResource(id = R.color.orange),
    fab: @Composable (Modifier) -> Unit = {}
) {

    val nbPulsar = rememberSaveable {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {
        delay(3000)
        nbPulsar.intValue = 3
    }

    var fabSize by remember { mutableStateOf(IntSize(0, 0)) }

    val effects: List<Pair<Float, Float>> = List(nbPulsar.intValue) {
        pulsarBuilder(pulsarRadius = pulsarRadius, size = fabSize.width, delay = it * 500)
    }


    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier, onDraw = {
            for (i in 0 until nbPulsar.intValue) {
                val (radius, alpha) = effects[i]
                drawCircle(color = pulsarColor, radius = radius, alpha = alpha)
            }
        })
        fab(
            Modifier
                .padding((pulsarRadius * 2).dp)
                .onGloballyPositioned {
                    if (it.isAttached) {
                        fabSize = it.size
                    }
                }
        )
    }
}

@Composable
fun pulsarBuilder(pulsarRadius: Float, size: Int, delay: Int): Pair<Float, Float> {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val radius by infiniteTransition.animateFloat(
        initialValue = (size / 2).toFloat(),
        targetValue = size + (pulsarRadius * 2),
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(3000),
            initialStartOffset = StartOffset(delay),
            repeatMode = Restart
        ), label = ""
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(3000),
            initialStartOffset = StartOffset(delay + 100),
            repeatMode = Restart
        ), label = ""
    )

    return radius to alpha
}