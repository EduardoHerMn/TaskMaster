package com.example.taskmaster2

import android.content.Context
import android.icu.util.Calendar

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

}