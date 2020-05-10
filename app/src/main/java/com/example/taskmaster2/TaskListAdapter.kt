package com.example.taskmaster2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.example.taskmaster2.R

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var data =  mutableListOf<JsonObject>()

    fun TaskListAdapter(context:Context, data: MutableList<JsonObject>){
        this.context = context
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.task_item,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TaskListAdapter.ViewHolder, position: Int) {
        //var item:JsonObject = data.get(position)
        var item:JsonObject = data.get(position).asJsonObject
        holder.bind(item,context)
    }


    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        private var imagen: ImageView = view.findViewById(R.id.task_imagen)
        private var titulo: TextView   = view.findViewById(R.id.task_titulo)
        private var lugar: TextView   = view.findViewById(R.id.task_lugar)
        private var fecha: TextView   = view.findViewById(R.id.task_fecha)
        private var hora: TextView   = view.findViewById(R.id.task_hora)

        fun bind(item: JsonObject, context: Context){
            //aquí iría el load del objeto cuando esté terminada la api
            imagen.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.taskimagen))
            titulo.text = item.get("titulo").asString
            lugar.text = item.get("lugar").asString
            fecha.text = item.get("fecha").asString
            hora.text = item.get("hora").asString
        }
    }

}
