package com.example.workoutapp.database

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsRepository(private val context: Context) {

    companion object {
        val LANGUAGE_KEY  = stringPreferencesKey("language")
        val KCAL_KEY      = stringPreferencesKey("target_kcal")
        val PROTEIN_KEY   = stringPreferencesKey("target_protein")
        val FATS_KEY      = stringPreferencesKey("target_fats")
        val CARBS_KEY     = stringPreferencesKey("target_carbs")
    }

    val targetKcal:    Flow<String> = context.dataStore.data.map { it[KCAL_KEY]     ?: "2700" }
    val targetProtein: Flow<String> = context.dataStore.data.map { it[PROTEIN_KEY]  ?: "150"  }
    val targetFats:    Flow<String> = context.dataStore.data.map { it[FATS_KEY]     ?: "80"   }
    val targetCarbs:   Flow<String> = context.dataStore.data.map { it[CARBS_KEY]    ?: "344"  }

    suspend fun setTargetKcal(v: String)    { context.dataStore.edit { it[KCAL_KEY]     = v } }
    suspend fun setTargetProtein(v: String) { context.dataStore.edit { it[PROTEIN_KEY]  = v } }
    suspend fun setTargetFats(v: String)    { context.dataStore.edit { it[FATS_KEY]     = v } }
    suspend fun setTargetCarbs(v: String)   { context.dataStore.edit { it[CARBS_KEY]    = v } }

    val language: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[LANGUAGE_KEY] ?: getCurrentAppLocale() }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = lang
        }
    }

    private fun getCurrentAppLocale(): String {
        val locales = AppCompatDelegate.getApplicationLocales()
        return if (!locales.isEmpty) {
            locales[0]?.language ?: "en"
        } else {
            "en" // fallback gdy nigdy nie ustawiono - locale systemowe
        }
    }
}