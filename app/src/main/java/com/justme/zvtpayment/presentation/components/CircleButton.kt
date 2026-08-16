package com.justme.zvtpayment.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircleButton(number: String, onClick: (String) -> Unit) {
    val isDelete = number == "12"
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.90f else 1.0f,
        label = "scale"
    )

    val shape = RoundedCornerShape(20.dp)

    val containerColor = if (isDelete) {
        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.85f)
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
    }
    val contentColor = if (isDelete) {
        MaterialTheme.colorScheme.onErrorContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    val borderStroke = if (isDelete) {
        BorderStroke(1.5.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.40f))
    } else {
        BorderStroke(1.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
    }

    Surface(
        onClick = { onClick(number) },
        modifier = Modifier
            .size(74.dp)
            .scale(scale),
        shape = shape,
        color = containerColor,
        tonalElevation = if (isPressed) 1.dp else 4.dp,
        shadowElevation = if (isPressed) 0.5.dp else 1.dp,
        border = borderStroke,
        interactionSource = interactionSource
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isDelete) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Backspace,
                    tint = contentColor,
                    contentDescription = "delete button",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp)
                )
            } else {
                Text(
                    text = when (number) {
                        "11" -> "0"
                        "10" -> "."
                        else -> number
                    },
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = contentColor
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ShowPreview() {
}