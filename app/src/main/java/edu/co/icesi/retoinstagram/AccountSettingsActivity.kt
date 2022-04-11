package edu.co.icesi.retoinstagram

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.FileProvider
import edu.co.icesi.retoinstagram.databinding.ActivityAccountSettingsBinding
import edu.co.icesi.retoinstagram.RetoInstagram.Companion.miBaseDeDatos
import edu.co.icesi.retoinstagram.fragments.NavigationScreenActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AccountSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountSettingsBinding

    private lateinit var cerrarSesion: Button
    private lateinit var cerrarPerfil: ImageView
    var user = miBaseDeDatos.getUsuarioLogueado()

    private var file : File? = null
    private var imgPath : String? =null
    private var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    var FILE_NAME = "photo_" + timestamp + "_"

    val launcherCamera = registerForActivityResult(StartActivityForResult(), ::onCameraResutl)
    val galleryLauncher = registerForActivityResult(StartActivityForResult(), ::onGalleryResult)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        cerrarPerfil = findViewById(R.id.cerrarPerfilbtn)
        cerrarSesion = findViewById(R.id.cerrarSesion_btn)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }

        checkDetails()

        cerrarSesion.setOnClickListener {


            val intent = Intent(this, InicioSesionActivity::class.java)
            startActivity(intent)
            miBaseDeDatos.guardarNoMeOlvides(false)
            miBaseDeDatos.cerrarSesion()
            finish()
        }

        binding.guardarCambiosbtn.setOnClickListener {
            updateUserInfo()

            Toast.makeText(
                this,
                "Account information has been updated successfully",
                Toast.LENGTH_LONG
            ).show()

            val intent = Intent(this, NavigationScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cerrarPerfilbtn.setOnClickListener {
            val intent = Intent(this, NavigationScreenActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.cambiarFotoCamarabtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            file = File("${getExternalFilesDir(null)}/$FILE_NAME")
            val uri = FileProvider.getUriForFile(this, packageName, file!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
           launcherCamera.launch(intent)
        }

        binding.cambiarFotoGaleriabtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type ="image/*"
            galleryLauncher.launch(intent)
        }
    }



    private fun updateUserInfo() {

        if (!(binding.nombreUsuarioET.text.toString() == "" || binding.nombrePerfilET.text.toString() == "")) {


            user!!.nombre = binding.nombreUsuarioET.text.toString()
            user!!.nombreUsuario = binding.nombrePerfilET.text.toString()
            user!!.biografia = binding.descripcionBioET.text.toString()

            if(!imgPath.isNullOrEmpty()&&!imgPath.contentEquals(user?.foto)){
                user!!.foto = imgPath!!
            }


            miBaseDeDatos.actualizarUsuario(user!!)

        } else {
            Toast.makeText(this, "Username and name are required", Toast.LENGTH_LONG).show()
        }
    }

    fun checkDetails() {
        binding.nombreUsuarioET.setText(user?.nombre)
        binding.nombrePerfilET.setText(user?.nombreUsuario)
        binding.descripcionBioET.setText(user?.biografia)


    }
    private fun onCameraResutl(activityResult: ActivityResult) {

        //thumbnail
        //val bitmap = activityResult.data?.extras?.get("data") as Bitmap
        //binding.profileImageViewProfilefragment.setImageBitmap(bitmap)

        if(activityResult.resultCode == RESULT_OK){
            imgPath = file?.path!!
            val bitmap = BitmapFactory.decodeFile(file?.path)
            val thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.width/4, bitmap.height/4, true)
            binding.editFotoPerfilView.setImageBitmap(thumbnail)
        }else if(activityResult.resultCode== RESULT_CANCELED){
            Toast.makeText(this, "No tomó foto", Toast.LENGTH_SHORT).show()
        }
    }

    fun onGalleryResult(result: ActivityResult){


        if(result.resultCode== RESULT_OK){
            val uriImage = result.data?.data
            imgPath = UtilDomi.getPath(this, uriImage!!)
            uriImage?.let{
                binding.editFotoPerfilView.setImageURI(uriImage)
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}