package com.justme.zvtpayment.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircleButton(number: String, onClick: (String) -> Unit) {
    OutlinedButton(
        onClick = { onClick(number) },
        modifier = Modifier
            .size(60.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.White),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.Blue,
            containerColor = Color.White
        )
    ) {
        if (number == "12") {
            Icon(
                imageVector = Icons.Outlined.Backspace,
                tint = Color.Black,
                contentDescription = "delete button"
            )

        } else {
            Text(
                text = if (number == "11") "0" else if (number == "10") "." else number,
                color = Color.Black, fontWeight = FontWeight.Bold,
                fontSize = 19.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ShowPreview() {
}