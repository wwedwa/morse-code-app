package com.github.wwedwa.morsecodeapp.screens

import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    selectedOutput: OutputType,
    onOutputToggle: (OutputType) -> Unit,
    onMorseButtonPress: () -> Unit,
    selectedTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit,
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Morse Code Emitter",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onMorseButtonPress,
                modifier = Modifier
                    .size(200.dp)
            ) {
                Text("Emit")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Flashlight")
                Switch(
                    checked = selectedOutput == OutputType.SPEAKER,
                    onCheckedChange = {
                        val newOutput = if (it) OutputType.SPEAKER else OutputType.FLASHLIGHT
                        onOutputToggle(newOutput)
                    }
                )
                Text("Speaker")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}