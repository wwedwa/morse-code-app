package com.github.wwedwa.morsecodeapp.screens

import android.content.Context
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.wwedwa.morsecodeapp.enums.BottomNavTab
import com.github.wwedwa.morsecodeapp.enums.OutputType
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.github.wwedwa.morsecodeapp.viewmodels.MainViewModel

@Composable
fun SandboxScreen(viewModel: MainViewModel) {

    val context = LocalContext.current
    val outputType by viewModel.outputType

    // For detecting the button press/hold
    var toneGen by remember { mutableStateOf<ToneGenerator?>(null) }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Morse Code Generator",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            Log.d("Home Screen", "Button pressed")

                            // On press down
                            if (outputType == OutputType.FLASHLIGHT) {
                                toggleFlashlight(context, true)
                            } else {
                                toneGen = playTone()
                            }

                            tryAwaitRelease()

                            // On release
                            if (outputType == OutputType.FLASHLIGHT) {
                                toggleFlashlight(context, false)
                            } else {
                                toneGen?.stopTone()
                                toneGen?.release()
                                toneGen = null
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Flashlight", modifier = Modifier.padding(end = 8.dp))
            Switch(
                checked = outputType == OutputType.SPEAKER,
                onCheckedChange = { viewModel.toggleOutput() }
            )
            Text("Speaker", modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

fun toggleFlashlight(context: Context, turnOn: Boolean) {
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraId = cameraManager.cameraIdList.firstOrNull() ?: return
    try {
        cameraManager.setTorchMode(cameraId, turnOn)
    } catch (e: Exception) {
        Log.e("Flashlight", "Error toggling flashlight", e)
    }
}

// Speaker tone logic
fun playTone(): ToneGenerator {
    val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
    toneGen.startTone(ToneGenerator.TONE_DTMF_S, 5000) // 5 sec max, stop early
    return toneGen
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