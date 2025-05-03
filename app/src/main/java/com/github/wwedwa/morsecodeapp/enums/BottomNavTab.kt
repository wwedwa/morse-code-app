package com.github.wwedwa.morsecodeapp.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavTab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    TRANSLATE("Translate", Icons.Filled.Language),
    DICTIONARY("Dictionary", Icons.AutoMirrored.Filled.List),
    LEARN("Learn", Icons.Filled.School)
}