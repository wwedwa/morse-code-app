package com.github.wwedwa.morsecodeapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.wwedwa.morsecodeapp.enums.BottomNavTab
import com.github.wwedwa.morsecodeapp.enums.OutputType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
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
                        BottomNavTab.TRANSLATE -> { navController.navigate("translate") }
                        BottomNavTab.DICTIONARY -> { navController.navigate("dictionary") }
                    }
                }
            )
        }
    )  { paddingValues ->
        NavHost(navController, startDestination = "sandbox", Modifier.padding(paddingValues)) {
            composable("sandbox") { SandboxScreen(viewModel) }
            composable("translate") { TranslatorScreen() }
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