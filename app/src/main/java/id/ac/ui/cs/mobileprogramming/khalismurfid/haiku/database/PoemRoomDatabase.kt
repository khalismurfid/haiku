package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.dao.*
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Poem class
@Database(entities = [Poem::class, Location::class, Tag::class], version = 1, exportSchema = false)
public abstract class PoemRoomDatabase : RoomDatabase() {

    abstract fun poemDao(): PoemDao
    abstract fun locationDao(): LocationDao
    abstract fun tagDao(): TagDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PoemRoomDatabase? = null

        fun getDatabase(context: Context,  scope: CoroutineScope): PoemRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PoemRoomDatabase::class.java,
                    "room_database"
                ).addCallback(PoemRoomDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class PoemRoomDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.tagDao())
                }
            }
        }

        suspend fun populateDatabase(tagDao: TagDao ) {

            // Add sample words.
            var tag = Tag("Happy")
            tagDao.insertTags(tag)
            tag = Tag("Sad")
            tagDao.insertTags(tag)
            tag = Tag("Fear")
            tagDao.insertTags(tag)
            tag = Tag("Angry")
            tagDao.insertTags(tag)

        }
    }
}

