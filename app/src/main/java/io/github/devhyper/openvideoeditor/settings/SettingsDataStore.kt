package io.github.devhyper.openvideoeditor.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class SettingsDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val THEME = stringPreferencesKey("theme")
        val LEGACY_FILE_PICKER = booleanPreferencesKey("legacy_file_picker")
        val UI_CASCADING_EFFECT = booleanPreferencesKey("ui_cascading_effect")
        val AMOLED_DARK_THEME = booleanPreferencesKey("amoled_dark_theme")
    }

    fun getThemeBlocking(): String {
        return runBlocking {
            val preferences = context.dataStore.data.first()
            preferences[THEME] ?: "System"
        }
    }

    fun getThemeAsync(): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[THEME] ?: "System"
            }
    }

    suspend fun setTheme(value: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME] = value
        }
    }

    fun getAmoledBlocking(): Boolean {
        return runBlocking {
            val preferences = context.dataStore.data.first()
            preferences[AMOLED_DARK_THEME] ?: false
        }
    }

    fun getAmoledAsync(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[AMOLED_DARK_THEME] ?: false
            }
    }

    suspend fun setAmoled(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AMOLED_DARK_THEME] = value
        }
    }

    fun getLegacyFilePickerBlocking(): Boolean {
        return runBlocking {
            val preferences = context.dataStore.data.first()
            preferences[LEGACY_FILE_PICKER] ?: false
        }
    }

    suspend fun setLegacyFilePicker(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[LEGACY_FILE_PICKER] = value
        }
    }

    fun getUiCascadingEffectBlocking(): Boolean {
        return runBlocking {
            val preferences = context.dataStore.data.first()
            preferences[UI_CASCADING_EFFECT] ?: false
        }
    }

    fun getUiCascadingEffectAsync(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[UI_CASCADING_EFFECT] ?: false
            }
    }

    suspend fun setUiCascadingEffect(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[UI_CASCADING_EFFECT] = value
        }
    }
}
