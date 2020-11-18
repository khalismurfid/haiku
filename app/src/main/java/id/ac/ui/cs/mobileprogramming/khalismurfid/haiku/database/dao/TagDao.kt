package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Tag
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.TagWithPoems

@Dao
interface TagDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTags(vararg tags: Tag) : Long

    @Update
    suspend fun updateTags(vararg tags: Tag) : Long

    @Delete
    suspend fun deleteTags(vararg tags: Tag) : Long

    @Query("SELECT * FROM TAG")
    fun loadAllTags(): LiveData<Array<Tag>>

    @Transaction
    @Query("SELECT * FROM TAG")
    fun getTagsWithPoems(): LiveData<List<TagWithPoems>>
}