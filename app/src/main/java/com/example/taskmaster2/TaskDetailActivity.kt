package com.example.taskmaster2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        detail_fecha.text = fecha
        detail_hora.text = hora
        detail_ubicacion.text = lugar

    }
}
