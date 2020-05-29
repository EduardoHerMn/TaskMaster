package com.example.taskmaster2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.util.Log
import android.view.View
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult


class Inicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        // Si no existe inicio de sesión se regresa a Main Activity
        // if (AccessToken.getCurrentAccessToken() == null) {

            //startActivity(Intent(applicationContext, MainActivity::class.java))

            //finish()

        //}

        var btnLogout = findViewById<Button>(R.id.btnLogout)
        var btnAgregarTarea = findViewById<Button>(R.id.btnAgregarTarea)

        btnLogout.setOnClickListener(View.OnClickListener {
            // Logout y regresa a Main Activity
            LoginManager.getInstance().logOut()
            startActivity(Intent(applicationContext, MainActivity::class.java))


        })



        btnAgregarTarea.setOnClickListener(View.OnClickListener {

            startActivity(Intent(applicationContext, TaskRegisterTask::class.java))


        })

    }
}



