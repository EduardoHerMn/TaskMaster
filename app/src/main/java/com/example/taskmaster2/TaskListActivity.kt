package com.example.taskmaster2

import android.content.Intent
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.koushikdutta.ion.Ion
import com.example.taskmaster2.TaskListAdapter
import kotlinx.android.synthetic.main.activity_task_list.*

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import org.json.JSONArray
import org.json.JSONObject
import com.example.taskmaster2.R
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class TaskListActivity : AppCompatActivity(), TaskListAdapter.OnTaskItemClickListener  {

    //private lateinit var data: JsonArray
    private var data =  mutableListOf<JsonObject>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter:TaskListAdapter
    private lateinit var dataNoUso: JSONArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title="TASKS"

        initializeComponents()
        initializeListeners()
        initializeData()

    }

    fun initializeComponents(){

    }

    fun initializeListeners(){

    }

    fun initializeData() {
        /*
        Aquí se inicializará el data de tasks cuando esté lista la api
        Ion.with(this)
            .load("https://pokeapi.co/api/v2/pokemon/?offset=0&limit=964")
            .asJsonObject()
            .done { e, result ->
                if(e == null){
                    Log.i("Salida", result.getAsJsonArray("results").size().toString())
                    data = result.getAsJsonArray("results")
                    initializeList()
                }
            }
         */
        //por ahora usa data mock
        for(i in 1..10){
            val rootObject= JsonObject()
            rootObject.addProperty("titulo","Tarea 1")
            rootObject.addProperty("descripcion","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            rootObject.addProperty("fecha","15 de Mayo de 2020")
            rootObject.addProperty("hora","15:00")
            rootObject.addProperty("lugar","Salón 2001")
            data.add(rootObject)
        }

        initializeList()
    }

    fun initializeList(){
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.scrollToPosition(0)

        adapter = TaskListAdapter()
        adapter.TaskListAdapter(this, data, this)

        recycler_view_list.layoutManager = linearLayoutManager
        recycler_view_list.adapter = adapter
        recycler_view_list.itemAnimator = DefaultItemAnimator()

    }

    override fun OnItemClick(item: JsonObject, position: Int) {
        //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()
        val intent = Intent(this, TaskDetailActivity::class.java)

        intent.putExtra("titulo", item.get("titulo").asString)
        intent.putExtra("descripcion", item.get("descripcion").asString)
        intent.putExtra("fecha", item.get("fecha").asString)
        intent.putExtra("hora", item.get("hora").asString)
        intent.putExtra("lugar", item.get("lugar").asString)

        startActivity(intent)
    }

}
