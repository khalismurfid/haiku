package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.dao.LocationDao
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.dao.PoemDao
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.dao.TagDao
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Location
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Poem
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Tag
import kotlinx.coroutines.*

class PoemRepository(private val poemDao: PoemDao, private val locationDao: LocationDao, private val tagDao: TagDao) {
    val allPoems: LiveData<Array<Poem>> = poemDao.loadAllPoems()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPoem(poem: Poem) {
        poemDao.insertPoems(poem)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertLocation(location: Location) {
        locationDao.insertLocations(location)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTag(tag: Tag) {
        tagDao.insertTags(tag)
    }



}