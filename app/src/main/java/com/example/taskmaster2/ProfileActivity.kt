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
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.ByteArrayOutputStream

class ProfileActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.apply {
            val nombre = getString("Nombre", "")
            val email = getString("Email", "")


            editNombre.setText(nombre)
            editEmail.setText(email)

        }

    }


    fun saveData(v: View){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()



        editor
            .putString("Nombre", editNombre.text.toString())
            .putString("Email", editEmail.text.toString())
            .apply()

        val toast = Toast.makeText(applicationContext,"Saved", Toast.LENGTH_LONG)
        toast. setGravity(Gravity.TOP, 0, 140)
        toast.show()
    }



}