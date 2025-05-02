package com.github.wwedwa.morsecodeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.github.wwedwa.morsecodeapp.MorseCodeUtils
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.github.wwedwa.morsecodeapp.R

@Composable
fun TranslatorScreen() {
    var inputText by rememberSaveable { mutableStateOf("") }
    var morseText by remember { mutableStateOf("") }
    var play by remember { mutableStateOf(false)}
    var currentSymbolIndex by rememberSaveable { mutableStateOf(-1) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Morse Code Translator", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                morseText = MorseCodeUtils.textToMorse(it)
            },
            label = { Text("Enter message") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val onPlay = {
            play = !play
            // If at end of transmission, restart and play again
            if (currentSymbolIndex >= MorseCodeUtils.textToMorse(inputText).length - 1) {
                currentSymbolIndex = -1
                play = true
            }
            if (play) {
                MorseCodeUtils.play(inputText, currentSymbolIndex + 1) { index -> currentSymbolIndex = index }
            } else {
                MorseCodeUtils.release()
            }
        }

        val onStop = { MorseCodeUtils.release(); currentSymbolIndex = -1; inputText = ""; morseText = ""}

        ControlButtons(onPlay, onStop)
        MorseDisplay(morseText, currentSymbolIndex)
    }
}

@Composable
fun ControlButtons(
    onPlay: () -> Unit,
    onCancel: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { onPlay() },
            modifier = Modifier
                .size(48.dp) // Set the size of the button
                .background(MaterialTheme.colorScheme.primary, CircleShape)) {
            Icon(
                painter = painterResource(id = R.drawable.play_pause_icon),
                contentDescription = "Play and Pause",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        IconButton(onClick = onCancel,
                modifier = Modifier
                .size(48.dp) // Set the size of the button
                .background(MaterialTheme.colorScheme.primary, CircleShape)) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Cancel",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun MorseDisplay(morseCode: String, currentIndex: Int) {
    val styledText = buildAnnotatedString {
        morseCode.forEachIndexed { index, char ->
            withStyle(
                style = if (index == currentIndex)
                    SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)
                else
                    SpanStyle(color = Color.Black)
            ) {
                append(char)
            }
        }
    }

    Text(
        text = styledText,
        style = MaterialTheme.typography.bodyLarge,
        fontSize = 20.sp,
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TranslatorScreenPreview() {
    TranslatorScreen()
}

