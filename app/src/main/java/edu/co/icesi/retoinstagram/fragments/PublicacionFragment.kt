package edu.co.icesi.retoinstagram.fragments


import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.core.content.FileProvider
import edu.co.icesi.retoinstagram.Model.Publicacion
import edu.co.icesi.retoinstagram.RetoInstagram
import edu.co.icesi.retoinstagram.UtilDomi
import edu.co.icesi.retoinstagram.databinding.FragmentPublicacionBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class PublicacionFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding:FragmentPublicacionBinding? = null
    private val binding get() = _binding!!
    private var urlImagen : String? =null
    private var archivo : File? = null
    private lateinit var identificadorUnico : String
    private lateinit var listaCiudadesDeMiPatria : ArrayAdapter<String>
    private lateinit var ciudadSeleccionada :String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentPublicacionBinding.inflate(inflater, container, false)
        val view = binding.root

         listaCiudadesDeMiPatria= ArrayAdapter<String>(requireContext(),
            android.R.layout.simple_spinner_dropdown_item)

        listaCiudadesDeMiPatria.addAll(Arrays.asList("En Cali ve!!", "En algun lugar de Medallo NEA!!", "En tu cora bby!!", "En 1006 torre 3!!"))
        binding.ciudadesDeMiPatriaSpinner.onItemSelectedListener = this
        binding.ciudadesDeMiPatriaSpinner.adapter = listaCiudadesDeMiPatria


        binding.publicarBtn.setOnClickListener {

            val descripcionFoto = binding.descripcionET.text.toString()
            var usuarioLogueado = RetoInstagram.miBaseDeDatos.getUsuarioLogueado()
            val fechaPublicacion = Calendar.getInstance()

            if(urlImagen!=null){
                var publicacion = Publicacion(usuarioLogueado!!.identificadorUsuario,urlImagen!!, descripcionFoto, fechaPublicacion, ciudadSeleccionada)
                Toast.makeText(requireContext(), "La foto fue publicada mi so!!", Toast.LENGTH_SHORT).show()
                RetoInstagram.miBaseDeDatos.guardarPublicaciones(publicacion)
            }else{
                Toast.makeText(requireContext(), "Papi, pero toma la foto!!", Toast.LENGTH_SHORT).show()
            }

        }
        requestPermissions(arrayOf(Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE),1)


        val camaraLauncher = registerForActivityResult(StartActivityForResult(), ::resultadoCamara)
        val galeriaLauncher = registerForActivityResult(StartActivityForResult(), ::resultadoGaleria)


        binding.camaraBtn.setOnClickListener{
            val intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            identificadorUnico = UUID.randomUUID().toString()
            archivo = File("${context?.getExternalFilesDir(null)}/foto$identificadorUnico")
            val uri = FileProvider.getUriForFile(requireContext(), requireContext().packageName, archivo!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            camaraLauncher.launch(intent)
        }

        binding.galeriaBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type ="image/*"
            galeriaLauncher.launch(intent)
        }

        return view
    }


    fun resultadoCamara(result: ActivityResult){

        if(result.resultCode == RESULT_OK){
            urlImagen= archivo?.path
            val bitmap = BitmapFactory.decodeFile(archivo?.path)
            val thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.width/4, bitmap.height/4, true)

            binding.image.setImageBitmap(thumbnail)
        }else if(result.resultCode== RESULT_CANCELED){
            Toast.makeText(requireContext(), "Menor, no tomaste ninguna foto. Postate serio!!", Toast.LENGTH_LONG).show()
        }
    }


    fun resultadoGaleria(result: ActivityResult){


        if(result.resultCode==RESULT_OK){
            val uriImage = result.data?.data
            val bitmap :  Bitmap  = MediaStore.Images.Media.getBitmap(context?.contentResolver, uriImage)
            val thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.width/4, bitmap.height/4, true)
            binding.image.setImageBitmap(thumbnail)
            identificadorUnico = UUID.randomUUID().toString()
            archivo = File("${context?.getExternalFilesDir(null)}/ $identificadorUnico")
            this.urlImagen = archivo!!.path
            val sourcePath= UtilDomi.getPath(requireContext(), uriImage!!)
            copy(File(sourcePath), archivo)
        }
    }
    @Throws(IOException::class)
    fun copy(src: File?, dst: File?) {
        FileInputStream(src).use { `in` ->
            FileOutputStream(dst).use { out ->
                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    }


    interface OnNewPostListener{
        fun onNewPost(post: Publicacion)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
     @JvmStatic
        fun newInstance() =
            PublicacionFragment()


    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val ciudadDeMiPatriaPosicion = listaCiudadesDeMiPatria.getItem(p2)
        ciudadSeleccionada = ciudadDeMiPatriaPosicion!!
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}