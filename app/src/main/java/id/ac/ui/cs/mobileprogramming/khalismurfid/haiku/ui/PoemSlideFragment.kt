package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Poem
import kotlinx.android.synthetic.main.fragment_poem_slide.view.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PoemSlideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PoemSlideFragment(val poem: Poem) : Fragment() {
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
        val rootView = inflater.inflate(R.layout.fragment_poem_slide, container, false)
        rootView.image.setImageURI(Uri.parse(poem.image))
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = formatter.parse(poem.date)
        val date_format = SimpleDateFormat("dd MMMM yyyy").format(date!!) //08, Aug 2019

        rootView.poem_title.text = poem.title
        rootView.poem_date.text = date_format
        rootView.card_view.setOnClickListener{
            val intent = Intent(activity, PoemDetailActivity::class.java)
            intent.putExtra("title", poem.title)
            intent.putExtra("content", poem.content)
            intent.putExtra("date", date_format)
            intent.putExtra("imageUri", poem.image)
            intent.putExtra("locationId", poem.locationId.toInt())
            intent.putExtra("tagId", poem.tagId.toInt())
            startActivity(intent)
        }
        return rootView
    }
}