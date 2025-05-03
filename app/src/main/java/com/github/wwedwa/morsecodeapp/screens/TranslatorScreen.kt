package com.github.wwedwa.morsecodeapp.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.github.wwedwa.morsecodeapp.MorseCodeUtils
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.wwedwa.morsecodeapp.R
import com.github.wwedwa.morsecodeapp.viewmodels.TranslatorViewModel

@Composable
fun TranslatorScreen(viewModel: TranslatorViewModel = hiltViewModel()) {
    var play by remember { mutableStateOf(false)}
    val scrollState = rememberScrollState()

    Log.d("translator screen", viewModel.inputText.value)
    val inputText by viewModel.inputText
    val currentSymbolIndex by viewModel.currentSymbolIndex

    val onPlayPause = {
        play = !play
        // If at end of transmission, restart and play again
        if (currentSymbolIndex >= MorseCodeUtils.textToMorse(inputText).length - 1) {
            viewModel.setSymbolIndex(-1)
            play = true
        }
        if (play) {
            MorseCodeUtils.play(inputText, currentSymbolIndex + 1) { index ->
                viewModel.setSymbolIndex(index)
                // If at end, stop playing
                if (index >= MorseCodeUtils.textToMorse(inputText).length - 1) {
                    play = false
                }
            }
        } else {
            MorseCodeUtils.release()
        }
    }

    // Reset everything when cancel is selected
    val onStop = { MorseCodeUtils.release(); viewModel.setSymbolIndex(-1); viewModel.setInputText(""); play = false }

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
                onStop()
                viewModel.setInputText(it)
            },
            label = { Text("Enter message") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (inputText.isNotEmpty()) {
                    IconButton(onClick = onPlayPause) {
                        if (play) {
                            Icon(
                                painter = painterResource(id = R.drawable.pause_icon),
                                contentDescription = "Pause",
                                tint = Color(0xff2a4174)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Play",
                                tint = Color(0xff2a4174)
                            )
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onStop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "CLEAR",
                fontSize = 18.sp
            )
        }

        MorseDisplay(MorseCodeUtils.textToMorse(inputText), currentSymbolIndex)
    }

    DisposableEffect(Unit) {
        onDispose {
            // If screen is disposed and morse code is playing, pause it
            if (play) onPlayPause()
        }
    }
}

@Composable
fun MorseDisplay(morseCode: String, currentIndex: Int) {
    val styledText = buildAnnotatedString {
        morseCode.forEachIndexed { index, char ->
            // Highlight current symbol red so user can follow along
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

