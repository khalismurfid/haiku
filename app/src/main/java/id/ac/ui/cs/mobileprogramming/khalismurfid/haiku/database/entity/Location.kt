package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
    @ColumnInfo(name = "name") val name: String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0)