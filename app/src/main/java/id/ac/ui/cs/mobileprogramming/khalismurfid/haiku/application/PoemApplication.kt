package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.application

import android.app.Application
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.PoemRoomDatabase
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.repository.PoemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PoemApplication: Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { PoemRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy {    PoemRepository(database.poemDao(), database.locationDao(), database.tagDao()) }
}