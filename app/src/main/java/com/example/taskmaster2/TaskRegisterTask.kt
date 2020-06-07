package com.example.taskmaster2

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    lateinit var context: Context
    lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_register_task)

        context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

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
            val task = Task(
                id = null,
                titulo = "Tarea",
                descripcion = editText6.text.toString(),
                fecha = dateTv.text.toString(),
                hora = timeTv.text.toString(),
                lugar = editText12.text.toString(),
                owner = null
            )
            //llamar la api y obtener id de la tarea registrada

            //get año, mes dia hora minuto from datos
            val dateParts = dateTv.text.toString().split("/")
            val dia = dateParts[0].toInt()
            val mes = dateParts[1].toInt()
            Log.d("T", mes.toString())
            val ano = dateParts[2].toInt()
            val hourParts = timeTv.text.toString().split(":")
            val hora = hourParts[0].toInt()
            val min = hourParts[1].toInt()
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, ano)
            calendar.set(Calendar.MONTH, mes)
            calendar.set(Calendar.DAY_OF_MONTH, dia)
            calendar.set(Calendar.HOUR_OF_DAY, hora)
            calendar.set(Calendar.MINUTE, min)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            var millis = calendar.timeInMillis


            val mc = Calendar.getInstance()
            mc.timeInMillis = System.currentTimeMillis()
            mc.add(Calendar.SECOND, 3)


            var intent = Intent(context, Inicio::class.java)
            intent.putExtra("title", editText6.text.toString())
            intent.putExtra("content", "Programado a las " + timeTv.text.toString())
            var pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, mc.timeInMillis + (5*1000), pendingIntent)
            Log.d("Alarm",  "create "+ Date().toString())
            Log.d("T",  System.currentTimeMillis().toString() + " " + millis.toString())
            //val toast = Toast.makeText(this, System.currentTimeMillis().toString() + " " + millis.toString(), Toast.LENGTH_SHORT)
            val text = "Nuevo tarea guardada"
            val duration = Toast.LENGTH_SHORT

            //val toast = Toast.makeText(applicationContext, text, duration)
            //toast.show()


        })


        btnRegresar2.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()

            finish()

        })



    }


    fun setNotification(){

    }

    fun cancelNotification(){

    }

}
