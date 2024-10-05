package `in`.developingdeveloper.timeline.core.data.local

interface LocalPersistableDataSource {
    suspend fun save(key: String, value: String)
    suspend fun get(key: String): String?
}
