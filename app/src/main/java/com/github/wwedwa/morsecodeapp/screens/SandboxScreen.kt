package com.github.wwedwa.morsecodeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.wwedwa.morsecodeapp.enums.OutputType
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.wwedwa.morsecodeapp.MorseCodeUtils
import com.github.wwedwa.morsecodeapp.viewmodels.SandboxViewModel

@Composable
fun SandboxScreen(viewModel: SandboxViewModel = hiltViewModel()) {

    val outputType by viewModel.outputType

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Morse Code Generator",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(100.dp))

        // onPress and onRelease functions to handle what happens when
        // the morse code button is interacted with (allows speaker and flashlight)
        val onPress = {
            if (outputType == OutputType.FLASHLIGHT) {
                MorseCodeUtils.toggleFlashlight(context, true)
            } else {
                MorseCodeUtils.playTone()
            }
        }

        val onRelease = {
            if (outputType == OutputType.FLASHLIGHT) {
                MorseCodeUtils.toggleFlashlight(context, false)
            } else {
                MorseCodeUtils.release()
            }
        }

        MorseCodeButton(onPress,
            onRelease,
            viewModel,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape))

        Spacer(modifier = Modifier.height(24.dp))

        // Switch for allowing user of speakers and flashlight
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Flashlight", modifier = Modifier.padding(end = 8.dp))
            Switch(
                checked = outputType == OutputType.SPEAKER,
                onCheckedChange = { viewModel.toggleOutput() }
            )
            Text("Speaker", modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        val scrollState = rememberScrollState()
        MorseEnglishTextBox(viewModel,
            buttonsModifier = Modifier.align(Alignment.CenterHorizontally),
            textModifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
        )
    }
}