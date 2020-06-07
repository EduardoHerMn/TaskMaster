package com.example.taskmaster2

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.mapbox.api.geocoding.v5.models.CarmenFeature
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceSelectionListener
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.activity_task_register_task.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class TaskRegisterTask : AppCompatActivity() {
    val REQUEST_CODE_AUTOCOMPLETE = 4321

    //val ubicacion = findViewById<TextView>(R.id.ubicacion_picker)
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_register_task)

        //Ubicación
        //val ubicacion = findViewById<TextView>(R.id.ubicacion_picker)
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


        //Definir ubicación
        ubicacion_picker.setOnClickListener(View.OnClickListener {
            /*
            val intent = Intent(this, MapActivity::class.java)
            startActivityForResult(intent, 444)
            */
            val intent = PlaceAutocomplete.IntentBuilder()
                .accessToken("pk.eyJ1IjoiYWJyYWhhbTEyMzEiLCJhIjoiY2thdzNtcjB3MDcxNTJ5bzF4djNiMWhkMSJ9.6eDO1C3nd0aS5BF2KiyAzQ")
                .placeOptions(PlaceOptions.builder().backgroundColor(Color.WHITE).build())
                .build(this)
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE)


        })


        btnAgregarNuevaTarea.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, MyAlarmReceiver::class.java)
            intent.putExtra("title", editText6.text.toString())
            intent.putExtra("content", "Evento programado a las " + timeTv.text.toString())
            pendingIntent = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            // Setting the specific time for the alarm manager to trigger the intent, in this example, the alarm is set to go off at 23:30, update the time according to your need

            alarmManager.setExact(AlarmManager.RTC, Date().time + 5000, pendingIntent)

            /*
            // Starts the alarm manager
            alarmManager.setRepeating(
                AlarmManager.RTC,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
             */


            /*
            val text = "Nuevo tarea guardada"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            */

        })



        btnRegresar2.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()

            finish()

        })



    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            val feature = PlaceAutocomplete.getPlace(data)
            Toast.makeText(this, feature.text(), Toast.LENGTH_LONG).show()
            ubicacion_picker.setText(feature.text().toString())
        }
    }
}
