package com.github.wwedwa.morsecodeapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.wwedwa.morsecodeapp.enums.OutputType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SandboxViewModel @Inject constructor() : MorseCodeButtonViewModel() {
    private val _outputType = mutableStateOf(OutputType.SPEAKER)
    val outputType: State<OutputType> = _outputType

    fun toggleOutput() {
        _outputType.value = if (_outputType.value == OutputType.FLASHLIGHT) {
            OutputType.SPEAKER
        } else {
            OutputType.FLASHLIGHT
        }
    }
}