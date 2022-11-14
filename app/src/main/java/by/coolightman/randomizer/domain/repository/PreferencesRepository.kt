package by.coolightman.randomizer.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    suspend fun putInt(key: String, value: Int)

    fun getInt(key: String, defaultValue: Int): Flow<Int>

    suspend fun putString(key: String, value: String)

    fun getString(key: String, defaultValue: String): Flow<String>

    suspend fun putBoolean(key: String, value: Boolean)

    fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean>
}