package edu.co.icesi.retoinstagram.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.co.icesi.retoinstagram.AccountSettingsActivity
import edu.co.icesi.retoinstagram.RetoInstagram.Companion.miBaseDeDatos
import edu.co.icesi.retoinstagram.databinding.FragmentPerfilBinding

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.editarCuentaBTN.setOnClickListener {
            startActivity(Intent(context, AccountSettingsActivity::class.java))
        }

        verificarDatosUsuario()

        return view
    }

    fun verificarDatosUsuario() {

        binding.nombreUsuarioET.setText(miBaseDeDatos.getUsuarioLogueado()?.nombreUsuario)
        binding.descripcionBioET.setText(miBaseDeDatos.getUsuarioLogueado()?.biografia)
        binding.fotoPerfilFragment.setImageBitmap(BitmapFactory.decodeFile(miBaseDeDatos.getUsuarioLogueado()!!.foto))
        binding.usuarioFragment.setText(miBaseDeDatos.getUsuarioLogueado()?.nombreUsuario)
    }

    override fun onPause() {
        super.onPause()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PerfilFragment()
    }
}