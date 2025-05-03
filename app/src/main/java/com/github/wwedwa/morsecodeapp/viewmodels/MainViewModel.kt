package com.github.wwedwa.morsecodeapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.github.wwedwa.morsecodeapp.enums.BottomNavTab
import com.github.wwedwa.morsecodeapp.enums.OutputType

open class MainViewModel : ViewModel() {
    private val _selectedTab = mutableStateOf(BottomNavTab.HOME)
    val selectedTab: State<BottomNavTab> = _selectedTab

    fun setSelectedTab(tab: BottomNavTab) {
        _selectedTab.value = tab
    }
}