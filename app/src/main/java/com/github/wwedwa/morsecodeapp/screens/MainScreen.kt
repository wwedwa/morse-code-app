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
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.wwedwa.morsecodeapp.viewmodels.FlashcardViewModel
import com.github.wwedwa.morsecodeapp.viewmodels.MainViewModel
import com.github.wwedwa.morsecodeapp.viewmodels.SandboxViewModel
import com.github.wwedwa.morsecodeapp.viewmodels.TranslatorViewModel

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
fun MainScreen(
    mainViewModel: MainViewModel,
    sandboxViewModel: SandboxViewModel,
    translatorViewModel: TranslatorViewModel,
    flashCardViewModel: FlashcardViewModel) {

    val selectedTab by mainViewModel.selectedTab
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    mainViewModel.setSelectedTab(tab)
                    when(tab) {
                        BottomNavTab.HOME -> { navController.navigate("sandbox") }
                        BottomNavTab.TRANSLATE -> { navController.navigate("translate") }
                        BottomNavTab.DICTIONARY -> { navController.navigate("dictionary") }
                        BottomNavTab.LEARN -> { navController.navigate("learn") }
                    }
                }
            )
        }
    )  { paddingValues ->
        NavHost(navController, startDestination = "sandbox", Modifier.padding(paddingValues)) {
            composable("sandbox") { SandboxScreen(sandboxViewModel) }
            composable("translate") { TranslatorScreen(translatorViewModel) }
            composable("dictionary") { DictionaryScreen() }
            composable("learn") { FlashCardsScreen(flashCardViewModel) }
        }
    }
}