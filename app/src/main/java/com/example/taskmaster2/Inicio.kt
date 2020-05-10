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

        if (AccessToken.getCurrentAccessToken() == null) {

            startActivity(Intent(applicationContext, MainActivity::class.java))

            //finish()

        }

        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener(View.OnClickListener {
            // Logout
            LoginManager.getInstance().logOut()
            startActivity(Intent(applicationContext, MainActivity::class.java))


        })

    }
}



