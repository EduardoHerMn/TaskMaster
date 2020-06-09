package com.example.taskmaster2

import android.content.Context
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.net.Uri
import android.util.Log
import java.net.URI

class MyPreferences(context: Context){

    val PREFERENCE_NAME = "Preference"
    val AUTHORIZATION = "Authorization"
    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getAuthorization(): String? {
        return preference.getString(AUTHORIZATION, "0")
    }

    fun setAuthorization(token: String){
        val editor = preference.edit()
        editor.putString(AUTHORIZATION, token)
        editor.apply()
    }

    fun getTaskImage(id:Int):String?{
        val key = "task_image_$id"
        Log.d("KEY GETIMAGE:", key)
        return preference.getString(key, "0")

    }

    fun setTaskImage(id:Int, image: String){
        val editor = preference.edit()
        val key = "task_image_$id"

        editor.putString(key, image)
        editor.apply()
        Log.d("key: ", key )
        Log.d("BASE 64 set image ", image)
    }

    fun getTaskImage2(id:Int):String?{
        val key = "task_image2_$id"
        Log.d("KEY GETIMAGE:", key)
        return preference.getString(key, "0")

    }

    fun setTaskImage2(id:Int, image: String){
        val editor = preference.edit()
        val key = "task_image2_$id"
        editor.putString(key, image)
        editor.apply()
        Log.d("key: ", key )
        Log.d("BASE 64 set image ", image)
    }

}