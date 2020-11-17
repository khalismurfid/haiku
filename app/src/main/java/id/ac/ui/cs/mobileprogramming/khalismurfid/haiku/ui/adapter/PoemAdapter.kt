package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import kotlinx.android.synthetic.main.fragment_poem_slide.view.*
class PoemAdapter(private val list:ArrayList<String>) : RecyclerView.Adapter<PoemAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_poem,parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.poem_title.text= list[position]
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}