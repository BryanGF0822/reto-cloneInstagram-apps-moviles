package edu.co.icesi.retoinstagram.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.co.icesi.retoinstagram.Model.Publicacion
import edu.co.icesi.retoinstagram.R
import edu.co.icesi.retoinstagram.Viewholder.PublicacionViewHolder

class PublicacionAdapter : RecyclerView.Adapter<PublicacionViewHolder>() {

    private val publicaciones = mutableListOf<Publicacion>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicacionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.postrow, parent, false)
        val publicacionViewHolder = PublicacionViewHolder(view)
        return publicacionViewHolder
    }

    override fun onBindViewHolder(holder: PublicacionViewHolder, position: Int) {
        val postn = publicaciones[position]
       holder.bind(postn)
    }

    fun addPost(post: Publicacion){
        publicaciones.add(post)
        publicaciones.sortByDescending{
            it.fechaPost
        }
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return publicaciones.size
    }

    fun limpiarPublicaciones() {
        publicaciones.clear()
    }
}