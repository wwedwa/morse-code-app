package com.github.wwedwa.morsecodeapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.github.wwedwa.morsecodeapp.FlashcardData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlashcardViewModel @Inject constructor() : MorseCodeButtonViewModel() {
    private val _currentWord = mutableStateOf(FlashcardData.words.random())
    val currentWord: State<String> = _currentWord

    private val _showSolution = mutableStateOf(false)
    val showSolution: State<Boolean> = _showSolution

    fun toggleSolution() {
        _showSolution.value = !_showSolution.value
    }

    fun hideSolution() {
        _showSolution.value = false
    }

    fun loadNextWord() {
        _currentWord.value = FlashcardData.words.random()
    }
}