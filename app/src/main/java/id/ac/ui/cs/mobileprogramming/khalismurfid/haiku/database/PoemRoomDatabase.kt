package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.dao.*
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.*

// Annotates class to be a Room Database with a table (entity) of the Poem class
@Database(entities = [Poem::class, Location::class, Tag::class, LocationWithPoems::class, TagWithPoems::class], version = 1, exportSchema = false)
public abstract class PoemRoomDatabase : RoomDatabase() {

    abstract fun poemDao(): PoemDao
    abstract fun locationDao(): LocationDao
    abstract fun tagDao(): TagDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PoemRoomDatabase? = null

        fun getDatabase(context: Context): PoemRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PoemRoomDatabase::class.java,
                    "room_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}