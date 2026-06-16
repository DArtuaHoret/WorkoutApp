package com.example.workoutapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.example.workoutapp.data.Exercise

@Composable
fun Exercise.displayName(): String {
    val lang = LocalConfiguration.current.locales[0].language
    return if (lang == "en" && nameEn.isNotBlank()) nameEn else name
}