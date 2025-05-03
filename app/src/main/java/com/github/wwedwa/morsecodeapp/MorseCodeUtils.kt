package com.github.wwedwa.morsecodeapp

import android.content.Context
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object MorseCodeUtils {
    val letterToMorse = mapOf(
        'A' to ".-", 'B' to "-...", 'C' to "-.-.", 'D' to "-..", 'E' to ".",
        'F' to "..-.", 'G' to "--.", 'H' to "....", 'I' to "..", 'J' to ".---",
        'K' to "-.-", 'L' to ".-..", 'M' to "--", 'N' to "-.", 'O' to "---",
        'P' to ".--.", 'Q' to "--.-", 'R' to ".-.", 'S' to "...", 'T' to "-",
        'U' to "..-", 'V' to "...-", 'W' to ".--", 'X' to "-..-", 'Y' to "-.--",
        'Z' to "--..", '0' to "-----", '1' to ".----", '2' to "..---", '3' to "...--",
        '4' to "....-", '5' to ".....", '6' to "-....", '7' to "--...", '8' to "---..",
        '9' to "----."
    )

    val morseToLetter = letterToMorse.entries.associate { (letter, morse) -> morse to letter }

    private var playJob: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var toneGen: ToneGenerator? = null

    fun textToMorse(text: String): String {
        val toReturn = text.uppercase().replace(Regex("\\s+"), " ").mapNotNull {
            when {
                it == ' ' -> "/" // space between words
                letterToMorse.containsKey(it) -> letterToMorse[it]
                else -> null // ignore unsupported characters
            }
        }.joinToString(" ")
        Log.d("Morse Code Utils", toReturn)
        return toReturn
    }

    fun morseToText(text: String): String {
        if (text.isBlank()) {
            return ""
        }
        return text.trim(' ').split(" ").mapNotNull {
            when {
                it == "/" -> ' ' // space between words
                morseToLetter.containsKey(it) -> morseToLetter[it]
                else -> '_' // Put a blank for unknown morse code characters
            }
        }.joinToString("")
    }

    fun toggleFlashlight(context: Context, turnOn: Boolean) {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList.firstOrNull() ?: return
        try {
            cameraManager.setTorchMode(cameraId, turnOn)
            if (turnOn) Log.d("Flashlight", "FLASH")
            else Log.d("Flashlight", "STOP FLASH")
        } catch (e: Exception) {
            Log.e("Flashlight", "Error toggling flashlight", e)
        }
    }

    // Speaker tone logic
    fun playTone() {
        // Use another ToneGenerator so that it can be cancelled by the caller
        toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        toneGen?.startTone(ToneGenerator.TONE_DTMF_S, 1000) // 1 sec max, stop early
    }

    fun play(text: String, position: Int = 0, onSymbolPlayed: (Int) -> Unit = {}) {
        // Cancel existing playback if any
        release()
        toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        // Start new playback
        playJob = coroutineScope.launch {
            val morse = textToMorse(text)

            for (i in position until morse.length) {
                val symbol = morse[i]
                onSymbolPlayed(i)
                // Dot - 1 unit
                // Dash - 3 units
                // Intra-character pause - 1 unit
                // Inter-character pause - 3 units
                // Space - 7 units
                when (symbol) {
                    '.' -> {
                        toneGen?.startTone(ToneGenerator.TONE_DTMF_S)
                        delay(100)
                        toneGen?.stopTone()
                        delay(100)
                    }
                    '-' -> {
                        toneGen?.startTone(ToneGenerator.TONE_DTMF_S)
                        delay(300)
                        toneGen?.stopTone()
                        delay(100)
                    }
                    // 200 instead of 300 to account for 100ms pause after characters
                    ' ' -> delay(200)
                    // 200 instead of 700 to account for spaces on each side of slash
                    '/' -> delay(200)
                }
            }
            release()
        }
    }

    fun release() {
        playJob?.cancel()
        toneGen?.stopTone()
        toneGen?.release()
        toneGen = null
    }
}