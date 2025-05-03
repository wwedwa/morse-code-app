package com.github.wwedwa.morsecodeapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class MorseCodeButtonViewModel : ViewModel() {
    private val _morseText = mutableStateOf("")
    val morseText: State<String> = _morseText

    private val _onBackspace = mutableStateOf(false)
    val onBackspace: State<Boolean> = _onBackspace

    private val _lastReleaseTime = mutableLongStateOf(System.currentTimeMillis())
    val lastReleaseTime: State<Long> = _lastReleaseTime

    fun setMorseText(text: String) {
        _morseText.value = text
    }

    fun setOnBackspace(value: Boolean) {
        _onBackspace.value = value
    }

    fun setLastReleaseTime(time: Long) {
        _lastReleaseTime.longValue = time
    }
}