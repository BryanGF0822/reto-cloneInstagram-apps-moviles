package edu.co.icesi.retoinstagram

import android.content.Context
import com.google.gson.Gson
import edu.co.icesi.retoinstagram.Model.Publicacion
import edu.co.icesi.retoinstagram.Model.Usuario
import java.util.*

class Preferencias(val context: Context) {

    val NOMBRE_PREFERENCIAS ="Mydtb"
    val PUBLICACIONES = "posts"
    val baseDeDatos = context.getSharedPreferences(NOMBRE_PREFERENCIAS, 0)
    var identificacionUsuario : String? = null
    var usuarioLogueado = "LOGGED_USER"
    val RECUERDAME = "remember"
    val gson = Gson()


    fun crearUsuarioAplicacion(){
        if(baseDeDatos.getString("ALFA","").toString().isEmpty()||baseDeDatos.getString("BETA","").toString().isEmpty()){

            val usuarioAlfa = Usuario(UUID.randomUUID().toString(),"Alfa","El Alfa Guapi","Me la pela Apps #Es broma","")
            val usuarioBeta = Usuario(UUID.randomUUID().toString(),"Beta","El Beta Guapi","Me la suda Apps #Es broma","")
            val alfaString = gson.toJson(usuarioAlfa)
            val betaString = gson.toJson(usuarioBeta)
            baseDeDatos.edit().putString("ALFA", alfaString).apply()
            baseDeDatos.edit().putString("BETA", betaString).apply()
        }

        else return
    }

    fun logueoDeUsuario(user : String){
        baseDeDatos.edit().putString(usuarioLogueado, user).apply()
    }

    fun getUsuarioLogueado(): Usuario? {

        identificacionUsuario = baseDeDatos.getString(usuarioLogueado,"").toString()

        if(identificacionUsuario != null){
            val userString = baseDeDatos.getString(identificacionUsuario,"")
            return gson.fromJson(userString, Usuario::class.java)
        }

        else return null
    }

    fun getUsuarioPorId(uuid: String): Usuario {

        val usuarioAlfa = gson.fromJson(baseDeDatos.getString("ALFA",""), Usuario::class.java)
        val usuarioBeta = gson.fromJson(baseDeDatos.getString("BETA",""), Usuario::class.java)

        if(usuarioAlfa.identificadorUsuario.contentEquals(uuid)) return usuarioAlfa

        return usuarioBeta


    }

    fun getRecuerdame(): Boolean{
        return baseDeDatos.getBoolean(RECUERDAME, false)
    }

    fun getPublicaciones() : MutableList<Publicacion>{
        var listaDePublicaciones = mutableListOf<Publicacion>()
        val publicaciones = baseDeDatos.getString(PUBLICACIONES,"")

        if(publicaciones!!.isEmpty()) return listaDePublicaciones
        listaDePublicaciones = gson.fromJson(publicaciones, Array<Publicacion>::class.java).toMutableList()
        return listaDePublicaciones
    }

    fun actualizarUsuario(user: Usuario){

        val deUsuarioAString = gson.toJson(user)
        baseDeDatos.edit().putString(identificacionUsuario,deUsuarioAString).apply()
    }

    fun cerrarSesion(){

        baseDeDatos.edit().remove(usuarioLogueado).apply()
    }

    fun guardarNoMeOlvides(remember:Boolean){
        baseDeDatos.edit().putBoolean(RECUERDAME, remember).apply()
    }

    fun guardarPublicaciones(post: Publicacion){
       var publicaciones = getPublicaciones()
        publicaciones.add(post)

        publicaciones.sortByDescending {
            it.fechaPost
        }
        baseDeDatos.edit().putString(PUBLICACIONES, gson.toJson(publicaciones)).apply()

    }

}