package com.example.taskmaster2

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("key") val key: String?
)

data class User(
    @SerializedName("email") val email: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("password") val password: String?
)

data class UserList(
    val list: List<User>
)

data class UserRegister(
    val username: String?,
    val password1: String?,
    val password2: String?
)

data class Task(
    @SerializedName("id") val id : Int?,
    @SerializedName("titulo") val titulo : String?,
    @SerializedName("descripcion") val descripcion : String?,
    @SerializedName("fecha") val fecha : String?,
    @SerializedName("hora") val hora : String?,
    @SerializedName("lugar") val lugar : String?,
    @SerializedName("owner") val owner : String?,
    @SerializedName("terminada") val terminada : Boolean?,
    @SerializedName("fechaTerminada") val fechaTerminada : String?
)



data class TaskList(
    @SerializedName("lista") var list: List<Task>

)