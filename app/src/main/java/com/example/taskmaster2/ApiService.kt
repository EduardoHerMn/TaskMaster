package com.example.taskmaster2

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("rest-auth/registration/")
    fun registerUser(@Body userRegister: UserRegister): Call<Token>

    @GET("users/")
    fun getUsers(): Call<UserList>

    @POST("rest-auth/login/")
    fun UserLogin(@Body user: User): Call<Token>

    //requires token
    @POST("rest-auth/logout/")
    fun UserLogOut(): Call<Response<String>>

    //requires token
    @GET("users/me/")
    fun getUserProfile(@Header("Authorization")token: String?): Call<User>

    //requires token
    @GET("tasks/")
    fun UsersListTask(@Header("Authorization")token: String?): Call<List<Task>>

    //requires token

    @POST("tasks/")
    fun SaveTask(@Body task: Task, @Header("Authorization")token: String?): Call<Task>

    @GET("tasks/{id}/")
    fun getTaskById(@Path("id") id: Int,  @Header("Authorization")token: String?): Call<Task>

    @PUT("tasks/{id}/")
    fun UpdateTask(@Path("id") id: Int,  @Header("Authorization")token: String?, @Body task: Task) : Call<Task>

    @DELETE("tasks/{id}/")
    fun DeleteTask(@Path("id") id: Int,  @Header("Authorization")token: String?): Call<Void>







}