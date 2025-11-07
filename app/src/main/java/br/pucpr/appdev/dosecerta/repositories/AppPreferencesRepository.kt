package br.pucpr.appdev.dosecerta.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_prefs")

class AppPreferencesRepository(context: Context) {
    private val dataStore = context.dataStore
    private val HAS_ACCESSED_APP_BEFORE_KEY = booleanPreferencesKey(
        "has_accessed_app_before"
    )

    val hasAccessedBefore: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[HAS_ACCESSED_APP_BEFORE_KEY] ?: false
    }

    suspend fun setHasAccessedBefore(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[HAS_ACCESSED_APP_BEFORE_KEY] = value
        }
    }
}