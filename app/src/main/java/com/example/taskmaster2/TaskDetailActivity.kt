package com.example.taskmaster2

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions
import kotlinx.android.synthetic.main.activity_task_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class TaskDetailActivity : AppCompatActivity() {
    val REQUEST_CODE_AUTOCOMPLETE = 4321
    private lateinit var task : Task
    private var id :Int = 0
    private var owner :String = ""
    private var checkbox :Boolean = false
    private var fechaTermino :String = ""
    private var imageview: ImageView? = null
    private val IMAGE_DIRECTORY = "/demonuts"


    lateinit var context: Context

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var pendingIntent2: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        setSupportActionBar(toolbar_detail)
        toolbar_detail.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        supportActionBar?.title="Detalles de la Tarea"

        toolbar_detail.setNavigationOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, TaskListActivity::class.java))

        })

        val myPreferences = MyPreferences(this@TaskDetailActivity)
        id = intent.getIntExtra("id", 0 )
        context = this
        try{
            var imagen = myPreferences.getTaskImage(id)

            var b64b = decodeBase64(imagen)

            imageView3.setImageBitmap(b64b)

            var imagen2 = myPreferences.getTaskImage2(id)

            var b64b2 = decodeBase64(imagen2)

            imageView4.setImageBitmap(b64b2)


        } catch(e: Exception){
            e.printStackTrace()
            Toast.makeText(this@TaskDetailActivity, "No puedo recuperar la imagen!", Toast.LENGTH_SHORT).show()
        }


//        try {
//            val imagen = myPreferences.getTaskImage(id)
//            Log.d("ID TAL VEZ MALO", id.toString())
//            val imagen2 = Uri.parse(imagen)
//            Log.d("URI 2: ", imagen2.toString())
//            imageView3.setImageURI(imagen2)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toast.makeText(this@TaskDetailActivity, "No puedo recuperar la imagen!", Toast.LENGTH_SHORT).show()
//        }


//        val wallpaperDirectory = File(getExternalFilesDir(null).toString() + IMAGE_DIRECTORY)
//
//        var listImages : Array<File>? = null
//        listImages = wallpaperDirectory.listFiles()
//
//        if(listImages != null && listImages.size!! > 0){
//            imageView3.setImageBitmap(BitmapFactory.decodeFile(listImages[0].absolutePath))
//        }
//        else {
//            val img = BitmapFactory.decodeResource(resources, R.drawable.ic_arrow_back_white_24dp)
//            val round = BitmapDrawable(resources, img)
//            imageView3.setImageDrawable(round)
//            println("Image_Directory")
//            println(IMAGE_DIRECTORY)
//        }




        val request = ServiceBuilder.buildService(ApiService::class.java)
        //val imagen = myPreferences.getTaskImage(id)
        //var decoded = decodeBase64(imagen)
        val call = request.getTaskById(id, myPreferences.getAuthorization() )

        call.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.isSuccessful){
                    task = response.body()!!
                    editTitulo.setText(task.titulo)
                    editFecha.setText(task.fecha)
                    editHora.setText(task.hora)
                    editUbicacion.setText(task.lugar)
                    editDescription.setText(task.descripcion)
                    owner = task.owner.toString()
                    checkbox = task.terminada!!
                    fechaAcabada.setText( task.fechaTerminada)
                    //if (imagen == "0"){

                    //} else{
                    //    imageView3.setImageBitmap(decoded!!)
                    //}

                }else{
                    //Log.d("ABC", "aqki entro")
                    Toast.makeText(this@TaskDetailActivity, "Ocurrió un error, por favor revisa de nuevo tus datos", Toast.LENGTH_SHORT).show()
                    //toast de error
                }
            }
            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.d("ABC", "error de network o del server")
                //toast de error
                Toast.makeText(this@TaskDetailActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })



        // Fecha
        var fecha1 = findViewById<TextView>(R.id.editFecha)

        // Hora
        var hora1 = findViewById<TextView>(R.id.editHora)

        //Abrir calendario
        val c= Calendar.getInstance()
        //Definir Año, Mes y Día
        val year=c.get(Calendar.YEAR)
        val month=c.get(Calendar.MONTH)
        val day=c.get(Calendar.DAY_OF_MONTH)


        //Definir Hora
        hora1.setOnClickListener(View.OnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                editHora.setText(SimpleDateFormat("HH:mm").format(c.time))
            }
            TimePickerDialog(this, timeSetListener, c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE), true).show()
        })

        //Definir fecha
        fecha1.setOnClickListener(View.OnClickListener {
            val dpd=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view_, mYear, mMonth, mDay ->
                editFecha.setText(""+ mDay +"/"+ mMonth+ "/"+ mYear)
            }, year, month, day)
            dpd.show()
        })


        //Definir ubicación
        editUbicacion.setOnClickListener(View.OnClickListener {
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



        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = pref.edit()

        //Guardar Checkbox
        if(pref.contains("checked") && pref.getBoolean("checked",false) == true) {
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);

        }

        checkBox.setOnCheckedChangeListener { compoundButton, b ->
            if (checkBox.isChecked) {
                checkbox = true
                editor.putBoolean("checked", true)
                editor.apply()
            } else {
                checkbox = false
                editor.putBoolean("checked", false)
                editor.apply()
            }
        }






        //IMAGE PICK BUTTON CLICK
        imageView3.setOnClickListener{

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //PERMISSION DENIED
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request permission
                    requestPermissions(permissions, PERMISION_CODE);
                }
                else {
                    //permission already granted
                    pickImageFromGallery();

                }
            }
            else {
                //Systen OS is <= Marshmallow
                pickImageFromGallery();
            }
        }

        //IMAGE PICK BUTTON CLICK
        imageView4.setOnClickListener{

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //PERMISSION DENIED
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request permission
                    requestPermissions(permissions, PERMISION_CODE);
                }
                else {
                    //permission already granted
                    pickImageFromGallery2();

                }
            }
            else {
                //Systen OS is <= Marshmallow
                pickImageFromGallery2();
            }
        }


    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share ->{

                //Toast.makeText(applicationContext, "click on share", Toast.LENGTH_LONG).show()
                deleteTask()
                return true
            }
            R.id.action_exit ->{
                //Toast.makeText(applicationContext, "click on exit", Toast.LENGTH_LONG).show()
                saveData(View(this))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(intent, IMAGE_PICK_CODE)


    }

    private fun pickImageFromGallery2() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(intent, IMAGE_PICK_CODE2)


    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        private val IMAGE_PICK_CODE2 = 1002;
        //Permission code
        private val PERMISION_CODE = 10001;

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISION_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permissionn popup
                    pickImageFromGallery();
                }
                else {
                    //permissionfrom popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private fun deleteTask(){
        val myPreferences = MyPreferences(this@TaskDetailActivity)
        val request = ServiceBuilder.buildService(ApiService::class.java)
        val call = request.DeleteTask(id, myPreferences.getAuthorization() )

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful){
                    Toast.makeText(this@TaskDetailActivity, "Acción exitosa", Toast.LENGTH_SHORT).show()
                    cancelNotification(id)
                    val intent = Intent(context, Inicio::class.java)
                    startActivity(intent)
                }else{
                    //Log.d("ABC", "aqki entro")
                    Toast.makeText(this@TaskDetailActivity, "Ocurrió un error, por favor inténtalo más tarde", Toast.LENGTH_SHORT).show()
                    //toast de error
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("ABC", "error de network o del server")
                //toast de error
                //Toast.makeText(this@TaskDetailActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@TaskDetailActivity, "Error de red, por favor inténtalo más tarde", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setNotification(id:Int ){
        val idNot1 = id * 2;
        val idNot2 = id * 2 + 1;
        //get año, mes dia hora minuto from datos
        val dateParts = editFecha.text.toString().split("/")
        val dia = dateParts[0].toInt()
        val mes = dateParts[1].toInt()
        val ano = dateParts[2].toInt()
        val hourParts = editHora.text.toString().split(":")
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

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this@TaskDetailActivity, MyAlarmReceiver::class.java)
        intent.putExtra("title", editTitulo.text.toString())
        intent.putExtra("content", "Evento programado a las " + editHora.text.toString())
        intent.putExtra("id", idNot1)
        pendingIntent = PendingIntent.getBroadcast(this@TaskDetailActivity, idNot1, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        val intent2 = Intent(this@TaskDetailActivity, MyAlarmReceiver::class.java)
        intent2.putExtra("title", editTitulo.text.toString())
        intent2.putExtra("content", "Evento programado en 10 minutos ")
        intent2.putExtra("id", idNot2)
        pendingIntent2 = PendingIntent.getBroadcast(this@TaskDetailActivity, idNot2, intent2, PendingIntent.FLAG_UPDATE_CURRENT)

        // Setting the specific time for the alarm manager to trigger the intent, in this example, the alarm is set to go off at 23:30, update the time according to your need
        alarmManager.setExact(AlarmManager.RTC, millis, pendingIntent)
        alarmManager.setExact(AlarmManager.RTC, millis - (1000 * 60 * 10), pendingIntent2)

    }

    fun cancelNotification(id: Int){
        val idNot1 = id * 2;
        val idNot2 = id * 2 + 1;
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this@TaskDetailActivity, MyAlarmReceiver::class.java)
        intent.putExtra("id", idNot1)
        pendingIntent = PendingIntent.getBroadcast(this@TaskDetailActivity, idNot1, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val intent2 = Intent(this@TaskDetailActivity, MyAlarmReceiver::class.java)
        intent2.putExtra("id", idNot2)
        pendingIntent2 = PendingIntent.getBroadcast(this@TaskDetailActivity, idNot2, intent2, PendingIntent.FLAG_UPDATE_CURRENT)

        // Setting the specific time for the alarm manager to trigger the intent, in this example, the alarm is set to go off at 23:30, update the time according to your need
        alarmManager.cancel(pendingIntent)
        alarmManager.cancel(pendingIntent2)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val contentURI = data?.data
//            try
//            {
//                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
//                val round = BitmapDrawable(resources, bitmap)
//                val wallpaperDirectory = File(getExternalFilesDir(null).toString() + IMAGE_DIRECTORY)
//                wallpaperDirectory.deleteRecursively()
//                val path = saveImage(bitmap)
//                Toast.makeText(this@TaskDetailActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
//                imageView3.setImageDrawable(round)
//            }
//            catch (e: IOException) {
//                e.printStackTrace()
//                Toast.makeText(this@TaskDetailActivity, "Failed!", Toast.LENGTH_SHORT).show()
//            }

            Log.d("DATA:", data?.data.toString())
            val imageUri = data!!.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

            val b64 = encodeToBase64(bitmap)

            imageView3.setImageURI(data?.data)
            Log.d("BASE 64: ", b64)

            Log.d("URI", contentURI.toString())
            val myPreferences = MyPreferences(this@TaskDetailActivity)
            myPreferences.setTaskImage(id, b64!!)


        }

        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE2){
            val contentURI = data?.data
//            try
//            {
//                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
//                val round = BitmapDrawable(resources, bitmap)
//                val wallpaperDirectory = File(getExternalFilesDir(null).toString() + IMAGE_DIRECTORY)
//                wallpaperDirectory.deleteRecursively()
//                val path = saveImage(bitmap)
//                Toast.makeText(this@TaskDetailActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
//                imageView3.setImageDrawable(round)
//            }
//            catch (e: IOException) {
//                e.printStackTrace()
//                Toast.makeText(this@TaskDetailActivity, "Failed!", Toast.LENGTH_SHORT).show()
//            }

            Log.d("DATA:", data?.data.toString())
            val imageUri2 = data!!.data
            val bitmap2 = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri2)

            val b642 = encodeToBase64(bitmap2)

            imageView4.setImageURI(data?.data)
            Log.d("BASE 64: ", b642)

            Log.d("URI", contentURI.toString())
            val myPreferences2 = MyPreferences(this@TaskDetailActivity)
            myPreferences2.setTaskImage2(id, b642!!)


        }


        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            val feature = PlaceAutocomplete.getPlace(data)
            Toast.makeText(this, feature.text(), Toast.LENGTH_LONG).show()
            editUbicacion.setText(feature.text().toString())
        }

    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(getExternalFilesDir(null).toString() + IMAGE_DIRECTORY)
        println("WallpaperDirectory::" + wallpaperDirectory)

        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }




    //var c = Calendar.getInstance()     c.timeInMillis = System.getCurrentMillis()
    fun saveData(v: View){
        var c = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()
        var day = c.get(Calendar.DAY_OF_MONTH).toString()
        var month = (c.get(Calendar.MONTH)+1).toString()
        var year = c.get(Calendar.YEAR).toString()
        var hora = c.get(Calendar.HOUR).toString()
        var minuto = c.get(Calendar.MINUTE).toString()
        var final = ""
        if (checkbox){
             final = (day + '/' + month + '/' + year + "     " + hora + ":" + minuto)
        } else {
            final = "No terminado"
        }



        val  task1 = Task(
            id = id,
            titulo = editTitulo.text.toString(),
            descripcion = editDescription.text.toString(),
            fecha = editFecha.text.toString(),
            hora = editHora.text.toString(),
            lugar = editUbicacion.text.toString(),
            owner = task.owner,
            fechaTerminada = final,
            terminada = checkbox
        )


        val myPreferences1 = MyPreferences(this@TaskDetailActivity)
        val request1 = ServiceBuilder.buildService(ApiService::class.java)
        val call1 = request1.UpdateTask(id, myPreferences1.getAuthorization(), task1 )

        call1.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.isSuccessful){
                    //recibir token
                    setNotification(response.body()!!.id!!.toInt())
                    Toast.makeText(this@TaskDetailActivity, "Se han guardado los cambios", Toast.LENGTH_SHORT).show()

                    /*
                    val myPreferences = MyPreferences(this@TaskDetailActivity)
                    var token = "Token " + response.body()!!
                     */


                }else{
                    //Log.d("ABC", "aqki entro")
                    Toast.makeText(this@TaskDetailActivity, "Ocurrió un error, por favor revisa de nuevo tus datos", Toast.LENGTH_SHORT).show()
                    //toast de error
                }
            }
            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.d("ABC", "error de network o del server")
                //toast de error
                Toast.makeText(this@TaskDetailActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()



        editor
            .putString("Description", editDescription.text.toString())
//            .putString("Description2", editDescription2.text.toString())
            .putString("Titulo", editTitulo.text.toString())
            .apply()

        val toast = Toast.makeText(applicationContext,"Saved", Toast.LENGTH_LONG)
        toast. setGravity(Gravity.TOP, 0, 140)
        toast.show()
    }

    fun encodeToBase64(image: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        val imageEncoded: String = Base64.encodeToString(b, Base64.DEFAULT)
        Log.d("Image Log:", imageEncoded)
        return imageEncoded
    }

    fun decodeBase64(input: String?): Bitmap? {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory
            .decodeByteArray(decodedByte, 0, decodedByte.size)
    }






}
