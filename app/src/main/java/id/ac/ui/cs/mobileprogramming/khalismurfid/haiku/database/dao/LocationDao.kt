package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Location
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.LocationWithPoems

@Dao
interface LocationDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocations(vararg locations: Location) : Long

    @Update
    suspend fun updateLocations(vararg locations: Location) : Long

    @Delete
    suspend fun deleteUsers(vararg locations: Location) : Long

    @Query("SELECT * FROM LOCATION")
    fun loadAllLocations(): LiveData<Array<Location>>

    @Transaction
    @Query("SELECT * FROM LOCATION")
    fun getLocationsWithPoems(): LiveData<List<LocationWithPoems>>
}