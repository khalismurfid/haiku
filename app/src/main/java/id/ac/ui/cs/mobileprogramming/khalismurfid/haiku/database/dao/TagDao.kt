package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Location
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Tag
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.TagWithPoems

@Dao
interface TagDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTags(tags: Tag) : Long

    @Update
    suspend fun updateTags(tags: Tag) : Int

    @Delete
    suspend fun deleteTags(tags: Tag) : Int

    @Query("SELECT * FROM TAG")
    fun loadAllTags(): LiveData<Array<Tag>>

    @Query("SELECT * FROM TAG WHERE id = :tagId")
    suspend fun getTag(tagId: Int): Tag?


    @Transaction
    @Query("SELECT * FROM TAG")
    fun getTagsWithPoems(): LiveData<List<TagWithPoems>>
}