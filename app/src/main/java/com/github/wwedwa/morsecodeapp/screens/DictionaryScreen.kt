package com.github.wwedwa.morsecodeapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class MorseCodeEntry(val character: Char, val code: String)

val morseCodeList = listOf(
    MorseCodeEntry('A', ".-"),
    MorseCodeEntry('B', "-..."),
    MorseCodeEntry('C', "-.-."),
    MorseCodeEntry('D', "-.."),
    MorseCodeEntry('E', "."),
    MorseCodeEntry('F', "..-."),
    MorseCodeEntry('G', "--."),
    MorseCodeEntry('H', "...."),
    MorseCodeEntry('I', ".."),
    MorseCodeEntry('J', ".---"),
    MorseCodeEntry('K', "-.-"),
    MorseCodeEntry('L', ".-.."),
    MorseCodeEntry('M', "--"),
    MorseCodeEntry('N', "-."),
    MorseCodeEntry('O', "---"),
    MorseCodeEntry('P', ".--."),
    MorseCodeEntry('Q', "--.-"),
    MorseCodeEntry('R', ".-."),
    MorseCodeEntry('S', "..."),
    MorseCodeEntry('T', "-"),
    MorseCodeEntry('U', "..-"),
    MorseCodeEntry('V', "...-"),
    MorseCodeEntry('W', ".--"),
    MorseCodeEntry('X', "-..-"),
    MorseCodeEntry('Y', "-.--"),
    MorseCodeEntry('Z', "--..")
)

@Composable
fun DictionaryScreen() {
    // Use LazyColumn to create a scrollable list
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(morseCodeList) { entry ->
            // Each row in the list will display the character and its corresponding morse code
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = entry.character.toString(),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = entry.code,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MorseCodeScreenPreview() {
    DictionaryScreen()
}

