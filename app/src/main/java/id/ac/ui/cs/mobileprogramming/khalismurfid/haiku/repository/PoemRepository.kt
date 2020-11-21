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
    val allTags: LiveData<Array<Tag>> = tagDao.loadAllTags()
    val allPoems: LiveData<Array<Poem>> = poemDao.loadAllPoems()
    val allLocations: LiveData<Array<Location>> = locationDao.loadAllLocations()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPoem(poem: Poem) {
        poemDao.insertPoems(poem)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertLocation(location: Location): Long {
        return locationDao.insertLocations(location)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getLocationByName(name: String) : Location?{
        return locationDao.getLocation(name)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun loadAllPoemWithTagId(tagId: Int) : Array<Poem>{
        return poemDao.loadAllPoemWithTagId(tagId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun loadAllPoemWithLocationId(locationId: Int) : Array<Poem>{
        return poemDao.loadAllPoemWithLocationId(locationId)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun loadLocationById(locationId: Int) : Location? {
        return locationDao.getLocation(locationId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun loadTagById(tagId: Int) : Tag? {
        return tagDao.getTag(tagId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun loadPoemById(poemId: Int) : Poem? {
        return poemDao.getPoem(poemId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTag(tag: Tag) {
        tagDao.insertTags(tag)
    }



}