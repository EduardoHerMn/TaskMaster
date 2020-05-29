package com.example.taskmaster2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callbackManager = CallbackManager.Factory.create()
        var btnLoginFacebook = findViewById<Button>(R.id.btnLoginFacebook)
        var btnRegister = findViewById<Button>(R.id.btnRegister)
        var btnLogin = findViewById<Button>(R.id.btnLogin)
        var viewTaskRegister=findViewById<Button>(R.id.ViewTaskRegister)

        btnLoginFacebook.setOnClickListener(View.OnClickListener {
            // Login

           // LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                        startActivity(Intent(applicationContext, Inicio::class.java))

                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")

                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError.")

                    }
                })
        })

        /*
        Click listener sigue de login -> registros -> task list,
        lo puse solo para probar mis cambios, se cambiará cuando se termine de implementar toda
         */
        btnLogin.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TaskListActivity::class.java)
            startActivity(intent)
        })



        btnRegister.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        })

        viewTaskRegister.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Inicio::class.java)
            startActivity(intent)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}