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
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import org.json.JSONArray
import org.json.JSONObject
import com.example.taskmaster2.R
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_registro.*
import kotlinx.android.synthetic.main.activity_task_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskListActivity : AppCompatActivity(), TaskListAdapter.OnTaskItemClickListener  {

    //private lateinit var data: JsonArray
    private var data =  mutableListOf<JsonObject>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter:TaskListAdapter
    private lateinit var dataNoUso: JSONArray
    private lateinit var lista: List<Task>



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



        val request = ServiceBuilder.buildService(ApiService::class.java)
        val call = request.UsersListTask()

        call.enqueue(object : Callback<TaskList> {
            override fun onResponse(call: Call<TaskList>, response: Response<TaskList>) {
                if (response.isSuccessful){
                   lista = response.body()!!.list
                    initializeList()
                }else{
                    //Log.d("ABC", "aqki entro")
                    Toast.makeText(this@TaskListActivity, "Ocurrió un error, por favor revisa de nuevo tus datos", Toast.LENGTH_SHORT).show()
                    //toast de error
                }
            }
            override fun onFailure(call: Call<TaskList>, t: Throwable) {
                Log.d("ABC", "error de network o del server")
                //toast de error
                Toast.makeText(this@TaskListActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


        //initializeList()
    }

    fun initializeList(){
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.scrollToPosition(0)

        adapter = TaskListAdapter()
        adapter.TaskListAdapter(this, lista, this)

        recycler_view_list.layoutManager = linearLayoutManager
        recycler_view_list.adapter = adapter
        recycler_view_list.itemAnimator = DefaultItemAnimator()

    }

    override fun OnItemClick(item: Task, position: Int) {
        //Toast.makeText(this, item.get("url").toString(), Toast.LENGTH_SHORT).show()
        val intent = Intent(this, TaskDetailActivity::class.java)

        intent.putExtra("id", item.id)
        startActivity(intent)
    }

}
