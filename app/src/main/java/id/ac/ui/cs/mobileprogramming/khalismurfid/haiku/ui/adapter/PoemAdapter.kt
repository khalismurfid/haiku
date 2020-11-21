package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.adapter


import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.common.Common.*
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Poem
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.AlbumDetailFragment
import kotlinx.android.synthetic.main.item_poem.view.*

class PoemAdapter(private val list:ArrayList<AlbumCollection>, val context: Context) : RecyclerView.Adapter<PoemAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_poem,parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.poem_subtitle.text = list[position].name
        val firstPoemImage = list[position].listPoems[0].image
        holder.view.image_poem.setImageURI(Uri.parse(firstPoemImage))
        holder.view.poem_title.visibility = View.GONE
        holder.view.card_view.setOnClickListener {
            val appCompatActivity = context as AppCompatActivity
            val fragment = AlbumDetailFragment(list[position].listPoems, list[position].name)
            appCompatActivity. supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.design_bottom_sheet_slide_in,
                    R.anim.design_bottom_sheet_slide_out
                )
                .replace(R.id.content_main, fragment, fragment.javaClass.simpleName)
                .addToBackStack("ALBUM_DETAIL_TAG")
                .commit()
        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}