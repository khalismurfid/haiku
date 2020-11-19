package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isNotEmpty
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.application.PoemApplication
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Poem
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Tag
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.viewmodel.PoemViewModel
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.viewmodel.PoemViewModelFactory
import kotlinx.android.synthetic.main.activity_compose_poem.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ComposePoemActivity : AppCompatActivity() {
    private val poemViewModel: PoemViewModel by viewModels {
        PoemViewModelFactory((application as PoemApplication).repository)
    }
    private var poemPhoto: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_poem)
        button_choose_photo.setOnClickListener {
            pickImage()
        }
        setSupportActionBar(toolbar)
        supportActionBar?.title = Html.fromHtml("<font color='#ffffff'>Compose Poem</font>")
        poemViewModel.allTags.observe(this, Observer { tags ->
            // Update the cached copy of the words in the adapter.
            tags?.let {
               for (tag: Tag in tags){
                   val chip = LayoutInflater.from(this).inflate(R.layout.chip,null) as Chip
                   chip.text = tag.name
                   chip.id = tag.id
                   chip_group.addView(chip)
               }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(com.google.android.material.R.anim.abc_slide_in_bottom, com.google.android.material.R.anim.abc_slide_out_bottom);
    }

    private fun pickImage() {
        if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) ==  PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            intent.putExtra("crop", "true")
            intent.putExtra("scale", true)
            intent.putExtra("aspectX", 16)
            intent.putExtra("aspectY", 9)
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST_CODE
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                return
            }
            val selectedImage: Uri? = data?.data
            poemPhoto = selectedImage.toString()
            image_view.setImageURI(selectedImage)
            image_view.visibility = View.VISIBLE
        }
    }

//    fun insertPoem(){
//            if(title_input.text !=null && content_input.text !=null && poemPhoto != ""){
//                val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
//                val dateString: String = simpleDateFormat.format(Date())
//                val poem : Poem = Poem(title_input.text.toString(), content_input.text.toString(), dateString, poemPhoto, )
//                poemViewModel.insert(poem)
//            }
//    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // pick image after request permission success
                    pickImage()
                }
            }
        }
    }
    private fun uriToImageFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                cursor.close()
                return File(filePath)
            }
            cursor.close()
        }
        return null
    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
    }

    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 1000
        const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    }


    }