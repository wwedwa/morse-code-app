package com.github.wwedwa.morsecodeapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.wwedwa.morsecodeapp.screens.MainScreen
import com.github.wwedwa.morsecodeapp.ui.theme.MorseCodeAppTheme
import com.github.wwedwa.morsecodeapp.viewmodels.FlashcardViewModel
import com.github.wwedwa.morsecodeapp.viewmodels.MainViewModel
import com.github.wwedwa.morsecodeapp.viewmodels.SandboxViewModel
import com.github.wwedwa.morsecodeapp.viewmodels.TranslatorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val sandboxViewModel: SandboxViewModel = hiltViewModel()
            val translatorViewModel: TranslatorViewModel = hiltViewModel()
            val flashCardViewModel: FlashcardViewModel = hiltViewModel()
            MorseCodeAppTheme {
                MainScreen(mainViewModel, sandboxViewModel, translatorViewModel, flashCardViewModel)
            }
        }
    }
}