package com.example.workoutapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.R

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val language by viewModel.language.collectAsState()
    val targetKcal    by viewModel.targetKcal.collectAsState()
    val targetProtein by viewModel.targetProtein.collectAsState()
    val targetFats    by viewModel.targetFats.collectAsState()
    val targetCarbs   by viewModel.targetCarbs.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.settings_title),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.settings_language_label),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            LanguageButton(
                label = stringResource(R.string.settings_language_pl),
                selected = language == "pl",
                onClick = { viewModel.onLanguageChange("pl") },
                modifier = Modifier.weight(1f),
            )
            LanguageButton(
                label = stringResource(R.string.settings_language_en),
                selected = language == "en",
                onClick = { viewModel.onLanguageChange("en") },
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.settings_macro_goal),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        MacroInputRow(
            stringResource(R.string.settings_kcal_label),
            targetKcal,
            Color(0xFFE040FB),
            viewModel::onTargetKcalChange
        )
        MacroInputRow(
            stringResource(R.string.settings_protein_label),
            targetProtein,
            Color(0xFF40C4FF),
            viewModel::onTargetProteinChange
        )
        MacroInputRow(
            stringResource(R.string.settings_fats_label),
            targetFats,
            Color(0xFFFFD740),
            viewModel::onTargetFatsChange
        )
        MacroInputRow(
            stringResource(R.string.settings_carbs_label),
            targetCarbs,
            Color(0xFFB388FF),
            viewModel::onTargetCarbsChange
        )
    }
}

@Composable
private fun LanguageButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(52.dp)
            .border(
                width = 2.dp,
                color = if (selected) Color.White else Color(0xFF444444),
                shape = RoundedCornerShape(14.dp),
            ),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFF2C2C2C) else Color(0xFF1A1A1A),
            contentColor = if (selected) Color.White else Color(0xFF888888),
        ),
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}

@Composable
private fun MacroInputRow(
    label: String,
    value: String,
    accentColor: Color,
    onValueChange: (String) -> Unit,
) {
    var textFieldValue by remember(value) {
        mutableStateOf(TextFieldValue(text = value, selection = TextRange(value.length)))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(accentColor, shape = RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, color = Color.White, fontSize = 14.sp)
        }
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                val filtered = newValue.text.filter(Char::isDigit).take(5)
                textFieldValue = newValue.copy(
                    text = filtered,
                    selection = TextRange(filtered.length)
                )
                if (filtered != value) onValueChange(filtered)
            },
            modifier = Modifier.width(90.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = accentColor,
                unfocusedBorderColor = Color(0xFF444444),
                focusedTextColor     = Color.White,
                unfocusedTextColor   = Color.White,
                cursorColor          = accentColor,
            ),
            shape = RoundedCornerShape(10.dp),
        )
    }
}