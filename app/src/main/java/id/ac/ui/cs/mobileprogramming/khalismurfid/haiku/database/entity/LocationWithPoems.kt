package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity
import androidx.room.*


data class LocationWithPoems(
    @Embedded val location: Location,
    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )
    val poems: List<Poem>
)