package edu.co.icesi.retoinstagram
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.co.icesi.retoinstagram.Model.Publicacion

class InicioViewModel : ViewModel() {


    var publicaciones = MutableLiveData<MutableList<Publicacion>> ()

    init {

        publicaciones.postValue(RetoInstagram.miBaseDeDatos.getPublicaciones())
    }
}