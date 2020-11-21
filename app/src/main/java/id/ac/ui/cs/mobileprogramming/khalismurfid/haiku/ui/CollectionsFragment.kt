package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.common.Common.*
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Location
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Tag
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.adapter.PoemAdapter
import kotlinx.android.synthetic.main.fragment_collections.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CollectionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CollectionsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_collections, container, false)
        rootView.recyclerview_album.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false);
        val activity = activity as MainActivity
        rootView.recyclerview_album.adapter =
            context?.let { PoemAdapter(ArrayList<AlbumCollection>(), it) }
        activity.poemViewModel.allTags.observe(activity, Observer { tags ->
            // Update the cached copy of the words in the adapter.
            tags?.let {
                val listAlbum = ArrayList<AlbumCollection>()
                for (tag: Tag in tags){
                    activity.poemViewModel.getAllPoemWithTagId(tag.id).observe(activity, Observer { poems ->
                        if(poems.isNotEmpty()){
                            val album = AlbumCollection(poems, tag.name)
                            listAlbum.add(album)
                            rootView.recyclerview_album.adapter =
                                context?.let { it1 -> PoemAdapter(listAlbum, it1) }
                        }
                    })
                }
            }
        })
        rootView.recyclerview_location.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false);

        rootView.recyclerview_location.adapter =
            context?.let { PoemAdapter(ArrayList<AlbumCollection>(), it) }
        activity.poemViewModel.allLocations.observe(activity, Observer { locations ->
            // Update the cached copy of the words in the adapter.
            locations?.let {
                val listAlbum = ArrayList<AlbumCollection>()
                for (location: Location in locations){
                    activity.poemViewModel.getAllPoemWithLocationId(location.id).observe(activity, Observer { poems ->
                        if(poems.isNotEmpty()){
                            val album = AlbumCollection(poems, location.name)
                            listAlbum.add(album)
                            rootView.recyclerview_location.adapter =
                                context?.let { it1 -> PoemAdapter(listAlbum, it1) }
                        }
                    })
                }
            }
        })
//        rootView.recyclerview_location.adapter = PoemAdapter(arrayListOf("test", "test", "test"))
//        rootView.recyclerview_time.layoutManager = LinearLayoutManager(
//            context,
//            LinearLayoutManager.HORIZONTAL,
//            false);
//        rootView.recyclerview_time.adapter = PoemAdapter(arrayListOf("waktu", "hapus", "aku"))
        return rootView
    }

    companion object {
        fun newInstance(): CollectionsFragment {
            val fragment = CollectionsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}