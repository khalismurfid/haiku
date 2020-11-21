package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Poem
import kotlinx.android.synthetic.main.fragment_album_detail.view.*
import kotlinx.android.synthetic.main.fragment_poem_slide.view.*
import kotlinx.android.synthetic.main.item_poem.view.*
import kotlinx.android.synthetic.main.item_poem.view.card_view
import kotlinx.android.synthetic.main.item_poem.view.poem_title
import java.text.SimpleDateFormat


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumDetailFragment(val poems: Array<Poem>, val albumTitle: String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_album_detail, container, false)
        rootView.PoemGridView.adapter = context?.let { PoemsAdapter(it, poems) }
        rootView.album_title.text = albumTitle
        return rootView
    }

    inner class PoemsAdapter(context: Context, poems: Array<Poem>) :
        BaseAdapter() {
        private val mContext: Context = context
        private val poems: Array<Poem> = poems

        // 2
        override fun getCount(): Int {
            return poems.size
        }

        // 3
        override fun getItemId(position: Int): Long {
            return poems[position].id.toLong()
        }

        // 4
        override fun getItem(position: Int): Poem {
            return poems[position]
        }

        // 5
        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {
            val poem: Poem = getItem(position)
            val item_poem = LayoutInflater.from(context).inflate(R.layout.item_poem,null)
            item_poem.poem_title.text = poem.title
            item_poem.poem_subtitle.text = ""
            val poemImage = poem.image
            item_poem.image_poem.setImageURI(Uri.parse(poemImage))

            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val date = formatter.parse(poem.date)
            val date_format = SimpleDateFormat("dd MMMM yyyy").format(date!!) //08, Aug 2019
            item_poem.card_view.setOnClickListener {
                val intent = Intent(activity, PoemDetailActivity::class.java)
                intent.putExtra("title", poem.title)
                intent.putExtra("content", poem.content)
                intent.putExtra("date", date_format)
                intent.putExtra("imageUri", poem.image)
                intent.putExtra("locationId", poem.locationId.toInt())
                intent.putExtra("tagId", poem.tagId.toInt())
                startActivity(intent)
            }
            return item_poem
        }

    }
}