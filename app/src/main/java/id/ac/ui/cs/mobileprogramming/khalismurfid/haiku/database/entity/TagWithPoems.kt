package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity
import androidx.room.*


data class TagWithPoems(
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "id",
        entityColumn = "tagId"
    )
    val poems: List<Poem>
)