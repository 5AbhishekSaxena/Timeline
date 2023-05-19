package `in`.developingdeveloper.timeline.core.data.local.events

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import `in`.developingdeveloper.timeline.core.data.local.tags.PersistableTag

data class PersistableEventWithTags(
    @Embedded
    val event: PersistableEvent,
    @Relation(
        parentColumn = "event_id",
        entityColumn = "tag_id",
        associateBy = Junction(EventTagCrossRef::class),
    )
    val tags: List<PersistableTag>,
)
