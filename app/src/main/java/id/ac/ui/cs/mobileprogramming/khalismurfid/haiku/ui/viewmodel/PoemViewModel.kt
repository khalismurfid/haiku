package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Location
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Poem
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Tag
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.repository.PoemRepository
import kotlinx.coroutines.launch

class PoemViewModel(private val repository: PoemRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPoems: LiveData<Array<Poem>> = repository.allPoems
    val allTags: LiveData<Array<Tag>> = repository.allTags
    var currentLocationId: Long? = null

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertPoem(poem: Poem) = viewModelScope.launch {
        repository.insertPoem(poem)
    }
    fun createPoemWLocationAndTag(title:String, content: String, date: String, photo:String , location: String, tagId: Int) = viewModelScope.launch {
        var locationObject: Location? = repository.getLocationByName(location)
        if (locationObject != null){
            currentLocationId = locationObject.id.toLong()
        } else{
            locationObject = Location(location)
            currentLocationId = repository.insertLocation(locationObject)
        }
        repository.insertPoem(Poem(title, content, date, photo, currentLocationId!!, tagId.toLong()))
    }
}

class PoemViewModelFactory(private val repository: PoemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PoemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PoemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}