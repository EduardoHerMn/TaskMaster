package com.example.taskmaster2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_registro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btnRegisterNuevoUsuario.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()

            val  userRegister = UserRegister(
                username = editText5.text.toString(),
                password1 = editText8.text.toString(),
                password2 = editText10.text.toString()
            )

            val request = ServiceBuilder.buildService(ApiService::class.java)
            val call = request.registerUser(userRegister)
            print("abajo body")
            Log.d("T", call.request().body.toString())
            print("arriba body")
            call.enqueue(object : Callback<Token> {
            //call.enqueue(object : Callback<UserList> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.isSuccessful){
                        //recibir token
                        //Toast.makeText(this@Register, response.body()!!.key, Toast.LENGTH_SHORT).show()
                        Log.d("ABC", response.body()!!.key)
                        Log.d("ABC", "funcionaaa")

                    }else{
                        Log.d("ABC", "aqki entro")
                    }
                }
                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Log.d("ABC", "funcionaaa")
                    Toast.makeText(this@Register, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })


        })



        btnRegresar.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()

            finish()

        })


    }
}
