package com.github.wwedwa.morsecodeapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.github.wwedwa.morsecodeapp.enums.BottomNavTab
import com.github.wwedwa.morsecodeapp.enums.OutputType

open class MainViewModel : ViewModel() {
    // Backing state
    private val _outputType = mutableStateOf(OutputType.SPEAKER)
    val outputType: State<OutputType> = _outputType

    private val _selectedTab = mutableStateOf(BottomNavTab.HOME)
    val selectedTab: State<BottomNavTab> = _selectedTab

    fun toggleOutput() {
        _outputType.value = if (_outputType.value == OutputType.FLASHLIGHT) {
            OutputType.SPEAKER
        } else {
            OutputType.FLASHLIGHT
        }
    }

    fun setOutput(type: OutputType) {
        _outputType.value = type
    }

    fun setSelectedTab(tab: BottomNavTab) {
        _selectedTab.value = tab
    }
}