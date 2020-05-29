package com.example.taskmaster2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.activity_task_register_task.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class TaskRegisterTask : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_register_task)

        //Ubicación











       // Fecha
        val fecha = findViewById<TextView>(R.id.dateTv)

        // Hora
        val hora = findViewById<TextView>(R.id.timeTv)

        //Abrir calendario
        val c= Calendar.getInstance()
        //Definir Año, Mes y Día
        val year=c.get(Calendar.YEAR)
        val month=c.get(Calendar.MONTH)
        val day=c.get(Calendar.DAY_OF_MONTH)


        //Definir Hora
        hora.setOnClickListener(View.OnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                timeTv.setText(SimpleDateFormat("HH:mm").format(c.time))
            }
            TimePickerDialog(this, timeSetListener, c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE), true).show()
        })

        //Definir fecha
        fecha.setOnClickListener(View.OnClickListener {
            val dpd=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view_, mYear, mMonth, mDay ->
                dateTv.setText(""+ mDay +"/"+ mMonth+ "/"+ mYear)
            }, year, month, day)
            dpd.show()
        })




        btnAgregarNuevaTarea.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()
            val text = "Nuevo tarea guardada"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()


        })



        btnRegresar2.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()

            finish()

        })



    }
}
