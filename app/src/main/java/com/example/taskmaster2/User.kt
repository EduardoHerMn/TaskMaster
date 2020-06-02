package com.example.taskmaster2

import android.telecom.Call
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/rest-auth/registration/")
    fun UserRegistration(email: String, username: String, password1: String, password2: String, name:String): Call<List<Post>>

    @POST("/rest-auth/login/")
    fun UserLogin(username: String, password: String): Call<Post>

    @POST("/rest-auth/logout/")
    fun UserLogOut(): Call<Post>

    @GET("/users/")
    fun UsersList(): Call<Post>

    @GET("/rest-auth/users/me")
    fun UserInfo(): Call<Post>

    @GET("/")
    fun UsersListTask(): Call<Post>

    @POST("/")
    fun SaveTask(titulo:String, descripcion:String, lugar:String, fecha: String, hora: String): Call<Post>

    GET("/{id}")
    fun SeeInfoTask(): Call<Post>







}