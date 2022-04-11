package edu.co.icesi.retoinstagram

import android.app.Application

class RetoInstagram:Application() {

    companion object{
        lateinit var miBaseDeDatos : Preferencias
    }
    override fun onCreate() {
        super.onCreate()
        miBaseDeDatos = Preferencias(applicationContext)
        miBaseDeDatos.crearUsuarioAplicacion()
    }
}