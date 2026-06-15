package com.example.workoutapp.ui.screens


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val language by viewModel.language.collectAsState()

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
                text = "Ustawienia",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                //modifier = Modifier.offset(x = (-16).dp),
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Język aplikacji",
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
                label = "🇵🇱  Polski",
                selected = language == "pl",
                onClick = { viewModel.onLanguageChange("pl") },
                modifier = Modifier.weight(1f),
            )
            LanguageButton(
                label = "🇬🇧  English",
                selected = language == "en",
                onClick = { viewModel.onLanguageChange("en") },
                modifier = Modifier.weight(1f),
            )
        }
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