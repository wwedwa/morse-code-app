package com.github.wwedwa.morsecodeapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TranslatorViewModel @Inject constructor() : ViewModel() {
    private val _inputText = mutableStateOf("")
    val inputText: State<String> = _inputText

    private val _currentSymbolIndex = mutableIntStateOf(-1)
    val currentSymbolIndex: State<Int> = _currentSymbolIndex

    fun setInputText(text: String) {
        _inputText.value = text
    }

    fun setSymbolIndex(index: Int) {
        _currentSymbolIndex.intValue = index
    }
}