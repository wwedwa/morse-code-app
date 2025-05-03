package com.github.wwedwa.morsecodeapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.wwedwa.morsecodeapp.MorseCodeUtils

@Composable
fun DictionaryScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        Text(
            text = "Morse Code Dictionary",
            style = MaterialTheme.typography.headlineSmall
        )

        // Row of alphanumeric characters. when clicked they will play their noise
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(MorseCodeUtils.letterToMorse.toList()) { entry ->
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 32.dp)
                            .clickable { MorseCodeUtils.play(entry.first.toString()) },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = entry.first.toString(),
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = entry.second,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MorseCodeScreenPreview() {
    DictionaryScreen()
}

