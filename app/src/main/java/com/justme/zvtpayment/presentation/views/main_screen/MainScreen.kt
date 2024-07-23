package com.justme.zvtpayment.presentation.views.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.justme.zvtpayment.presentation.components.CircleButton
import com.justme.zvtpayment.presentation.components.NumpadText

@Composable
fun MainScreen() {
    val numpadValue = remember { mutableStateOf("13.26") }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(1.0F)
        ) {
            Column(
                modifier = Modifier
                    .weight(1.0F)
                    .background(Color.White)
                    .fillMaxSize(1.0F),
                verticalArrangement = Arrangement.Bottom
            ) {
                NumpadText(numpadValue)
            }
            Column(
                modifier = Modifier
                    .weight(1.0F)
                    .fillMaxSize(1.0F)
                    .background(Color(0xFFd4deea))
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    items(count = 12) {
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircleButton(number = (it + 1).toString()) {
                                numpadValue.value += it
                            }

                        }
                    }
                }

            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MainScreen()
}