package edu.co.icesi.retoinstagram.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import edu.co.icesi.retoinstagram.R
import edu.co.icesi.retoinstagram.databinding.ActivityNavigationScreenBinding

class NavigationScreenActivity : AppCompatActivity() {

    private lateinit var inicioFragment: InicioFragment
    private lateinit var publicacionFragment: PublicacionFragment
    private lateinit var perfilFragment: PerfilFragment
    private lateinit var mercadoFragment: MercadoFragment
    private lateinit var buscarFragment: BuscarFragment
    private lateinit var mibinding: ActivityNavigationScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_screen)
        mibinding = ActivityNavigationScreenBinding.inflate(layoutInflater)
        val view = mibinding.root
        setContentView(view)
        inicioFragment = InicioFragment.newInstance()
        publicacionFragment = PublicacionFragment.newInstance()
        perfilFragment = PerfilFragment.newInstance()
        mercadoFragment = MercadoFragment.newInstance()
        buscarFragment = BuscarFragment.newInstance()

        mostrarFragment(inicioFragment)

        mibinding.barraNavegacion.setOnNavigationItemSelectedListener { menuItem ->

            if (menuItem.itemId == R.id.inicioitemMenu) {
                mostrarFragment(inicioFragment)
            } else if (menuItem.itemId == R.id.buscaritemMenu) {
                mostrarFragment(buscarFragment)
            } else if (menuItem.itemId == R.id.publicacionitemMenu) {
                mostrarFragment(publicacionFragment)
            } else if (menuItem.itemId == R.id.mercadoitemMenu) {
                mostrarFragment(mercadoFragment)
            } else if (menuItem.itemId == R.id.perfilitemMenu) {
                mostrarFragment(perfilFragment)
            }

            true
        }
    }

    private fun mostrarFragment(fragment: Fragment) {
        val transaccion = supportFragmentManager.beginTransaction()
        transaccion.replace(R.id.fragmentContainer, fragment)
        transaccion.commit()

    }
}