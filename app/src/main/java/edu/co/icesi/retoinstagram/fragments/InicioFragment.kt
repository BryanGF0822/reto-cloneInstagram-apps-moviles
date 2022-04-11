package edu.co.icesi.retoinstagram.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import edu.co.icesi.retoinstagram.Adapter.PublicacionAdapter
import edu.co.icesi.retoinstagram.InicioViewModel
import edu.co.icesi.retoinstagram.Model.Publicacion
import edu.co.icesi.retoinstagram.databinding.FragmentInicioBinding

class InicioFragment : Fragment(), PublicacionFragment.OnNewPostListener {

    private var _binding:FragmentInicioBinding? = null
    private val binding get() = _binding!!

    private lateinit var inicioViewModel : InicioViewModel

    private val adaptador = PublicacionAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)

        val view = binding.root
        val publicacionRecycler = binding.recyclerviewInicio
        publicacionRecycler.setHasFixedSize(true)
        publicacionRecycler.layoutManager = LinearLayoutManager(activity)
        publicacionRecycler.adapter = adaptador
        inicioViewModel= ViewModelProvider(this).get(InicioViewModel::class.java)
        inicioViewModel.publicaciones.observe(viewLifecycleOwner) {

            adaptador.limpiarPublicaciones()
            for (publicacion in it) {
                adaptador.addPost(publicacion)
            }
        }
        return view
    }


    override fun onResume() {
        super.onResume()
        adaptador.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
          @JvmStatic
        fun newInstance() =
            InicioFragment()
    }

    override fun onNewPost(post: Publicacion) {
        adaptador.addPost(post)
    }
}