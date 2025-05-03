package com.github.wwedwa.morsecodeapp.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.pointerInput
import com.github.wwedwa.morsecodeapp.viewmodels.MorseCodeButtonViewModel

@Composable
fun MorseCodeButton(
    onPress: () -> Unit,
    onRelease: () -> Unit,
    viewModel: MorseCodeButtonViewModel,
    modifier: Modifier) {
    val morseText by viewModel.morseText
    val onBackspace by viewModel.onBackspace
    val lastReleaseTime by viewModel.lastReleaseTime

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        val startTime = System.currentTimeMillis()
                        val pauseDuration = startTime - lastReleaseTime

                        // First draw letter/word breaks depending on when
                        // the button was last pressed
                        if (!onBackspace && morseText.isNotEmpty() && pauseDuration >= 700) {
                            viewModel.setMorseText("$morseText / ")
                        } else if (!onBackspace && morseText.isNotEmpty() && pauseDuration >= 300) {
                            viewModel.setMorseText("$morseText ")
                        }

                        viewModel.setOnBackspace(false)
                        onPress()

                        tryAwaitRelease()
                        val endTime = System.currentTimeMillis()
                        viewModel.setLastReleaseTime(endTime)

                        // On release
                        onRelease()

                        val duration = endTime - startTime
                        if (duration < 300) {
                            // It's a dot
                            viewModel.setMorseText("$morseText.")
                        } else {
                            // It's a dash
                            viewModel.setMorseText("$morseText-")
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Tap & Hold",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}