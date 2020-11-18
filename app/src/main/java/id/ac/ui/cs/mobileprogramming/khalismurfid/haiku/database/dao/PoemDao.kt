package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Poem

@Dao
interface PoemDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPoems(vararg poems: Poem) : Long

    @Update
    suspend fun updatePoems(vararg poems: Poem) : Long

    @Delete
    suspend fun deletePoems(vararg poems: Poem) : Long

    @Query("SELECT * FROM POEM")
    fun loadAllPoems(): LiveData<Array<Poem>>
}