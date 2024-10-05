package `in`.developingdeveloper.timeline.core.data.local.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import `in`.developingdeveloper.timeline.core.data.local.LocalPersistableDataSource
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreLocalPersistableDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalPersistableDataSource {

    override suspend fun save(key: String, value: String) {
        dataStore.edit { settings ->
            val preferenceKey = stringPreferencesKey(key)
            settings[preferenceKey] = value
        }
    }

    override suspend fun get(key: String): String? {
        val preferencesKey = stringPreferencesKey(key)
        return dataStore.data
            .filter { it.contains(preferencesKey) }
            .map { it[preferencesKey] }
            .firstOrNull()
    }
}
