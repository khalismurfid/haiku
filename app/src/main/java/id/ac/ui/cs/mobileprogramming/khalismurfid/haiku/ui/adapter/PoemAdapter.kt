package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.adapter


import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.common.Common.*
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Poem
import kotlinx.android.synthetic.main.fragment_poem_slide.view.*
import kotlinx.android.synthetic.main.item_poem.view.*

class PoemAdapter(private val list:ArrayList<AlbumCollection>) : RecyclerView.Adapter<PoemAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_poem,parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.poem_subtitle.text = list[position].name
        val firstPoemImage = list[position].listPoems[0].image
        holder.view.image_poem.setImageURI(Uri.parse(firstPoemImage))
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}