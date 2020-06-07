package com.example.taskmaster2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taskmaster2.R
import kotlinx.android.synthetic.main.activity_task_detail.*

class TaskDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        var titulo = intent.getStringExtra("titulo")
        var descripcion = intent.getStringExtra("descripcion")
        var fecha = intent.getStringExtra("fecha")
        var hora = intent.getStringExtra("hora")
        var lugar = intent.getStringExtra("lugar")

        detail_desc.text = descripcion
        detail_titulo.text = titulo

        detail_hora.text = hora
        detail_ubicacion.text = lugar


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

        }
    }
}
