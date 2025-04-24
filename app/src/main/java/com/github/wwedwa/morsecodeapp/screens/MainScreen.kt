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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.wwedwa.morsecodeapp.viewmodels.MainViewModel

@Composable
fun BottomNavigationBar(
    selectedTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit
) {
    NavigationBar {
        BottomNavTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = tab == selectedTab,
                onClick = { onTabSelected(tab) },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) }
            )
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val selectedTab by viewModel.selectedTab
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    viewModel.setSelectedTab(tab)
                    when(tab) {
                        BottomNavTab.HOME -> { navController.navigate("sandbox") }
                        BottomNavTab.SETTINGS -> { navController.navigate("dictionary") }
                        BottomNavTab.HISTORY -> { }
                    }
                }
            )
        }
    )  { paddingValues ->
        NavHost(navController, startDestination = "sandbox", Modifier.padding(paddingValues)) {
            composable("sandbox") { SandboxScreen(viewModel) }
            composable("dictionary") { DictionaryScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    // Create a "fake" or test ViewModel
    val previewViewModel = object : MainViewModel() {
        init {
            setOutput(OutputType.SPEAKER)
            setSelectedTab(BottomNavTab.HOME)
        }
    }

    MainScreen(previewViewModel)
}