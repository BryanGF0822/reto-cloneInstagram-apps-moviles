package edu.co.icesi.retoinstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import edu.co.icesi.retoinstagram.RetoInstagram.Companion.miBaseDeDatos
import edu.co.icesi.retoinstagram.fragments.NavigationScreenActivity

class InicioSesionActivity : AppCompatActivity() {

    private lateinit var correoElectronicoET : EditText
    private lateinit var contrasenaET : EditText
    private lateinit var iniciarSesionBTN : Button
    private lateinit var noMeOlvidesCheckBox : CheckBox

    var esRecordado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        correoElectronicoET = findViewById(R.id.correoElectronicoET)
        contrasenaET = findViewById(R.id.contrasenaET)
        iniciarSesionBTN = findViewById(R.id.iniciarSesionBTN)
        noMeOlvidesCheckBox = findViewById(R.id.noMeOlvidesCheckBox)
        esRecordado = miBaseDeDatos.getRecuerdame()

        if(esRecordado){
            val intent = Intent(this, NavigationScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        iniciarSesionBTN.setOnClickListener {
            val correo : String = correoElectronicoET.text.toString()
            val contrasena : String = contrasenaET.text.toString()
            val noOlvidar : Boolean = noMeOlvidesCheckBox.isChecked

            if((correo =="alfa@gmail.com" || correo == "beta@gmail.com") && contrasena == "aplicacionesmoviles"){
                if(correo=="alfa@gmail.com"){

                    miBaseDeDatos.guardarNoMeOlvides(noOlvidar)
                    miBaseDeDatos.logueoDeUsuario("ALFA")
                    val intent = Intent(this, NavigationScreenActivity::class.java )
                    startActivity(intent)
                    finish()

                }else{

                    miBaseDeDatos.logueoDeUsuario("BETA")
                    miBaseDeDatos.guardarNoMeOlvides(noOlvidar)

                    val intent = Intent(this, NavigationScreenActivity::class.java )
                    startActivity(intent)
                    finish()
                }
            }else{
                Toast.makeText(this, "Su usuario o contraseña son incorrectos, vuelva a intentarlo.", Toast.LENGTH_LONG).show()
            }
        }
    }
}