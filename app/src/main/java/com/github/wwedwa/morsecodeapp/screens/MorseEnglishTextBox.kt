package com.github.wwedwa.morsecodeapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.wwedwa.morsecodeapp.MorseCodeUtils
import com.github.wwedwa.morsecodeapp.R
import com.github.wwedwa.morsecodeapp.viewmodels.MorseCodeButtonViewModel

@Composable
fun MorseEnglishTextBox(
    viewModel: MorseCodeButtonViewModel,
    buttonsModifier: Modifier,
    textModifier: Modifier) {

    val morseText by viewModel.morseText

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = buttonsModifier
    ) {
        TextButton(
            onClick = { viewModel.setMorseText("") },
        ) {
            Text(
                text = "CLEAR",
                fontSize = 18.sp
            )
        }

        IconButton(onClick = {
            // Remove the last letter type (but not the space)
            viewModel.setMorseText(
                if (morseText.trim().contains(" ")) {
                    morseText.trim().substringBeforeLast(" ") + " "
                } else {
                    ""
                }
            )
            viewModel.setOnBackspace(true)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.backspace_icon),
                contentDescription = "Backspace",
                modifier = Modifier.size(30.dp),
                tint = Color(0xff2a4174)
            )
        }
    }

    Spacer(modifier = Modifier.height(64.dp))

    Column(
        modifier = textModifier
    ) {
        Text(
            text = morseText,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = MorseCodeUtils.morseToText(morseText),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
    }
}