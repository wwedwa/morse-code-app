package com.github.wwedwa.morsecodeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.wwedwa.morsecodeapp.MorseCodeUtils
import com.github.wwedwa.morsecodeapp.viewmodels.FlashcardViewModel

@Composable
fun FlashCardsScreen(viewModel: FlashcardViewModel = hiltViewModel()) {
    val currentWord by viewModel.currentWord
    val showSolution by viewModel.showSolution

    Column(
        modifier = Modifier
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Morse Code Flash Cards",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentWord,
                    style = MaterialTheme.typography.headlineMedium
                )
                if (showSolution) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        // Replace spaces with two so its easier to see separation
                        text = MorseCodeUtils.textToMorse(currentWord).replace(" ", "  "),
                        fontSize = 20.sp
                    )
                }
            }
        }

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            TextButton(onClick = { viewModel.toggleSolution() }) {
                Text(
                    text = if (showSolution) "Hide Solution" else "Show Solution",
                    fontSize = 18.sp
                )
            }

            TextButton(onClick = { viewModel.loadNextWord(); viewModel.setMorseText("") }) {
                Text(
                    text = "Next",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val onPress = { MorseCodeUtils.playTone() }

        val onRelease = { MorseCodeUtils.release() }

        MorseCodeButton(
            onPress,
            onRelease,
            viewModel,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
        )

        Spacer(modifier = Modifier.height(24.dp))

        val scrollState = rememberScrollState()
        MorseEnglishTextBox(viewModel,
            buttonsModifier = Modifier.align(Alignment.CenterHorizontally),
            textModifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .align(Alignment.CenterHorizontally)
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
        )
    }
}