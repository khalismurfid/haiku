package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poem")
data class Poem(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "locationId") val locationId: Long,
    @ColumnInfo(name = "tagId") val tagId: Long,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0)