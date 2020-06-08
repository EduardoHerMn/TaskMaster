package com.example.taskmaster2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_task_detail.*
import java.io.ByteArrayOutputStream


class TaskDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)


        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = pref.edit()

        //Guardar Checkbox
        if(pref.contains("checked") && pref.getBoolean("checked",false) == true) {
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);

        }

        checkBox.setOnCheckedChangeListener { compoundButton, b ->
            if (checkBox.isChecked) {
                editor.putBoolean("checked", true)
                editor.apply()
            } else {
                editor.putBoolean("checked", false)
                editor.apply()
            }
        }



        pref.apply {
            val description1 = getString("Description", "")
            val description2 = getString("Description2", "")
            val titulo = getString("Titulo", "")
            val fecha = getString("Fecha", "")
            val ubicacion = getString("Ubicacion", "")




            editDescription.setText(description1)
            editDescription2.setText(description2)
            editTitulo.setText(titulo)
            editFecha.setText(fecha)
            editUbicacion.setText(ubicacion)


        }

        var titulo = intent.getStringExtra("titulo")
        var descripcion = intent.getStringExtra("descripcion")
        var fecha = intent.getStringExtra("fecha")
        var hora = intent.getStringExtra("hora")
        var lugar = intent.getStringExtra("lugar")


        //IMAGE PICK BUTTON CLICK
        imageView3.setOnClickListener{

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //PERMISSION DENIED
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request permission
                    requestPermissions(permissions, PERMISION_CODE);
                }
                else {
                    //permission already granted
                    pickImageFromGallery();

                }
            }
            else {
                //Systen OS is <= Marshmallow
                pickImageFromGallery();
            }
        }

        //IMAGE PICK BUTTON CLICK
        imageView4.setOnClickListener{

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //PERMISSION DENIED
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request permission
                    requestPermissions(permissions, PERMISION_CODE);
                }
                else {
                    //permission already granted
                    pickImageFromGallery();

                }
            }
            else {
                //Systen OS is <= Marshmallow
                pickImageFromGallery();
            }
        }


    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)


    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISION_CODE = 10001;

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISION_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permissionn popup
                    pickImageFromGallery();
                }
                else {
                    //permissionfrom popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imageView3.setImageURI(data?.data);
            imageView4.setImageURI(data?.data);

        }
    }

    fun saveData(v: View){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()



        editor
            .putString("Description", editDescription.text.toString())
            .putString("Description2", editDescription2.text.toString())
            .putString("Titulo", editTitulo.text.toString())
            .putString("Fecha", editFecha.text.toString())
            .putString("Ubicacion", editUbicacion.text.toString())

            .apply()

        val toast = Toast.makeText(applicationContext,"Saved", Toast.LENGTH_LONG)
        toast. setGravity(Gravity.TOP, 0, 140)
        toast.show()
    }

    fun encodeToBase64(image: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        val imageEncoded: String = Base64.encodeToString(b, Base64.DEFAULT)
        Log.d("Image Log:", imageEncoded)
        return imageEncoded
    }

    fun decodeToBase64(input: String?): Bitmap? {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }



}
