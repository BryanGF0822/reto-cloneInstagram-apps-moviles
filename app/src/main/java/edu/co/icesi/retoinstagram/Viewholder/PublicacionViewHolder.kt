package edu.co.icesi.retoinstagram.Viewholder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.text.format.DateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.co.icesi.retoinstagram.Model.Publicacion
import edu.co.icesi.retoinstagram.R
import edu.co.icesi.retoinstagram.RetoInstagram
import java.time.Month
import java.util.*

class PublicacionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var nombreUsuarioPost : TextView = itemView.findViewById(R.id.nombreUsuarioPost)
    var descripcionPostET: TextView = itemView.findViewById(R.id.descripcionPostET)
    var fechitaPostET: TextView = itemView.findViewById(R.id.fechitaPostET)
    var ubicacionPostET: TextView = itemView.findViewById(R.id.ubicacionPostET)
    var imagenPublicacion : ImageView = itemView.findViewById(R.id.imagenPublicacion)
    var fotoPerfilPost : ImageView = itemView.findViewById(R.id.fotoPerfilPost)
    var nombreUsuarioAbajo : TextView = itemView.findViewById(R.id.nombreUsuarioAbajo)

    fun bind(post : Publicacion){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val mes =
            Month.of(post.fechaPost.get(Calendar.MONTH)+1).toString()

            val dia = post.fechaPost.get(Calendar.DAY_OF_MONTH).toString()
            val anio = post.fechaPost.get(Calendar.YEAR).toString()
            fechitaPostET.text = dia + "/" + mes + "/" + anio

        } else {
            val dia = DateFormat.format("dd", post.fechaPost.time) as String
            val mes = DateFormat.format("MMM", post.fechaPost.time) as String
            val anio = DateFormat.format("yyyy", post.fechaPost.time) as String
            fechitaPostET.text = dia + " " + mes+ " " + anio
        }


        val bitmap = BitmapFactory.decodeFile(post.imagenPost)
        val thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.width/4, bitmap.height/4, true)


        nombreUsuarioPost.text = RetoInstagram.miBaseDeDatos.getUsuarioPorId(post.identificacionUsuario).nombre
        descripcionPostET.text = post.descripcion
        ubicacionPostET.text = post.ciudad
        imagenPublicacion.setImageBitmap(thumbnail)
        fotoPerfilPost.setImageBitmap(BitmapFactory.decodeFile(RetoInstagram.miBaseDeDatos.getUsuarioPorId(post.identificacionUsuario).foto))
        nombreUsuarioAbajo.text = RetoInstagram.miBaseDeDatos.getUsuarioPorId(post.identificacionUsuario).nombreUsuario

    }
}