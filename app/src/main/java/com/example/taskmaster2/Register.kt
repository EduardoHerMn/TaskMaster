package com.example.taskmaster2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registro.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btnRegisterNuevoUsuario.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()
            val text = "Nuevo usuario registrado"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()


        })



        btnRegresar.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()

            finish()

        })


    }
}
