package com.example.workoutapp.ui.screens

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.database.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    val language: StateFlow<String> = settingsRepository.language
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "pl")

    val targetKcal:    StateFlow<String> = settingsRepository.targetKcal
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "2700")
    val targetProtein: StateFlow<String> = settingsRepository.targetProtein
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "150")
    val targetFats:    StateFlow<String> = settingsRepository.targetFats
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "80")
    val targetCarbs:   StateFlow<String> = settingsRepository.targetCarbs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "344")

    fun onTargetKcalChange(v: String)    { viewModelScope.launch { settingsRepository.setTargetKcal(v) } }
    fun onTargetProteinChange(v: String) { viewModelScope.launch { settingsRepository.setTargetProtein(v) } }
    fun onTargetFatsChange(v: String)    { viewModelScope.launch { settingsRepository.setTargetFats(v) } }
    fun onTargetCarbsChange(v: String)   { viewModelScope.launch { settingsRepository.setTargetCarbs(v) } }

    fun onLanguageChange(lang: String) {
        viewModelScope.launch {
            settingsRepository.setLanguage(lang)
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(lang)
            )
        }
    }


}