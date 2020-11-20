package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.common

import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Poem

class Common {
    companion object{
        const val LOCATION_RECEIVER_KEY = "location"
    }

    data class PoemCollection(val listPoems: ArrayList<Poem>, val name: String ){
    }
    data class AlbumCollection(val listPoems: Array<Poem>, val name: String ){
    }
}

