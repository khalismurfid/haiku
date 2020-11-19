package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Location
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.LocationWithPoems

@Dao
interface LocationDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocations(locations: Location) : Long

    @Update
    suspend fun updateLocations(locations: Location) : Int

    @Delete
    suspend fun deleteUsers(locations: Location) : Int

    @Query("SELECT * FROM LOCATION")
    fun loadAllLocations(): LiveData<Array<Location>>

    @Query("SELECT * FROM LOCATION WHERE name = :location")
    suspend fun getLocation(location: String): Location?

    @Transaction
    @Query("SELECT * FROM LOCATION")
    fun getLocationsWithPoems(): LiveData<List<LocationWithPoems>>
}