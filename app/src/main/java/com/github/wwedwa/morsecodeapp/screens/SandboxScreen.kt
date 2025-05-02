package com.github.wwedwa.morsecodeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
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
import com.github.wwedwa.morsecodeapp.enums.BottomNavTab
import com.github.wwedwa.morsecodeapp.enums.OutputType
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.TextButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.github.wwedwa.morsecodeapp.viewmodels.MainViewModel
import com.github.wwedwa.morsecodeapp.MorseCodeUtils
import com.github.wwedwa.morsecodeapp.R

@Composable
fun SandboxScreen(viewModel: MainViewModel) {

    var morseText by remember { mutableStateOf("") }
    var onBackspace by remember { mutableStateOf(false) }
    var lastReleaseTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val context = LocalContext.current
    val outputType by viewModel.outputType

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

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                val startTime = System.currentTimeMillis()
                                val pauseDuration = startTime - lastReleaseTime

                                // First draw letter/word breaks depending on when
                                // the button was last pressed
                                if (!onBackspace && morseText.isNotEmpty() && pauseDuration >= 700) {
                                    morseText += " / "
                                }
                                else if (!onBackspace && morseText.isNotEmpty() && pauseDuration >= 300) {
                                    morseText += " "
                                } //.... . .-.. .-.. --- /
                                // -- -.-- / -. .- -- . /
                                // .. ... / .-- .. .-.. .-.. .. .- --

                                onBackspace = false
                                // On press down
                                if (outputType == OutputType.FLASHLIGHT) {
                                    MorseCodeUtils.toggleFlashlight(context, true)
                                } else {
                                    MorseCodeUtils.playTone()
                                }

                                tryAwaitRelease()
                                val endTime = System.currentTimeMillis()
                                lastReleaseTime = endTime

                                // On release
                                if (outputType == OutputType.FLASHLIGHT) {
                                    MorseCodeUtils.toggleFlashlight(context, false)
                                } else {
                                    MorseCodeUtils.release()
                                }

                                val duration = endTime - startTime
                                morseText += if (duration < 300) {
                                    // It's a dot
                                    // Handle dot logic here
                                    "."
                                } else {
                                    // It's a dash
                                    // Handle dash logic here
                                    "-"
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

            Spacer(modifier = Modifier.height(24.dp))

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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                TextButton(
                    onClick = { morseText = "" },
                ) {
                    Text(
                        text = "CLEAR",
                        fontSize = 18.sp
                    )
                }

                IconButton(onClick = {
                    // Remove the last letter type (but not the space)
                    morseText = if (morseText.trim().contains(" ")) {
                        morseText.trim().substringBeforeLast(" ") + " "
                    } else {
                        ""
                    }
                    onBackspace = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.backspace_icon),
                        contentDescription = "Backspace",
                        modifier = Modifier.size(30.dp),
                        tint = Color(0xff2a4174)
                    )
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(16.dp, 0.dp, 16.dp, 0.dp)
            ) {
                Text(
                    text = morseText,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = MorseCodeUtils.morseToText(morseText),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp
                )
            }
        }
    }

@Preview(showBackground = true)
@Composable
fun PreviewSandboxcreen() {
    // Create a "fake" or test ViewModel
    val previewViewModel = object : MainViewModel() {
        init {
            setOutput(OutputType.SPEAKER)
            setSelectedTab(BottomNavTab.HOME)
        }
    }

    SandboxScreen(previewViewModel)
}