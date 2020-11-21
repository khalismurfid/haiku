package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.application.PoemApplication
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Tag
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.viewmodel.PoemViewModel
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.viewmodel.PoemViewModelFactory
import kotlinx.android.synthetic.main.activity_compose_poem.*
import kotlinx.android.synthetic.main.activity_poem_detail.*

class PoemDetailActivity : AppCompatActivity() {
    private val poemViewModel: PoemViewModel by viewModels {
        PoemViewModelFactory((application as PoemApplication).repository)
    }
    var title: String? = null
    var content: String? = null
    var imageUri: String? = null
    var date: String? = null
    var tagId: Int? = null
    var locationId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poem_detail)
        title = intent.getStringExtra("title")
        content = intent.getStringExtra("content")
        imageUri = intent.getStringExtra("imageUri")
        date = intent.getStringExtra("date")
        tagId = intent.getIntExtra("tagId", -1)
        locationId = intent.getIntExtra("locationId", -1)

        poem_title.text = title
        poem_content.text = content
        poem_image.setImageURI(Uri.parse(imageUri))

        poemViewModel.getLocation(locationId!!).observe(this, Observer { location ->
            // Update the cached copy of the words in the adapter.
            if (location!=null){
                poem_date_location.text = "${location.name}, $date"
            }
        })
        poemViewModel.getTag(tagId!!).observe(this, Observer { tag ->
            // Update the cached copy of the words in the adapter.
            if(tag!=null){
                poem_tag.text = "#${tag.name}"
            }
        })

    }
}