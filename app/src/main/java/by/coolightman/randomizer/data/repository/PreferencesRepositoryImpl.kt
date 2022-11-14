package by.coolightman.randomizer.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import by.coolightman.randomizer.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    override suspend fun putInt(key: String, value: Int) {
        val dataStoreKey = intPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override fun getInt(key: String, defaultValue: Int): Flow<Int> {
        val dataStoreKey = intPreferencesKey(key)
        return dataStore.data
            .map { preferences -> preferences[dataStoreKey] ?: defaultValue }
    }

    override suspend fun putString(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override fun getString(key: String, defaultValue: String): Flow<String> {
        val dataStoreKey = stringPreferencesKey(key)
        return dataStore.data
            .map { preferences -> preferences[dataStoreKey] ?: defaultValue }
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean> {
        val dataStoreKey = booleanPreferencesKey(key)
        return dataStore.data
            .map { preferences -> preferences[dataStoreKey] ?: defaultValue }
    }
}