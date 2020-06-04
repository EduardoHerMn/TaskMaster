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
    fun getUserProfile(): Call<User>

    //requires token
    @GET("")
    fun UsersListTask(): Call<TaskList>

    //requires token
    @POST("")
    fun SaveTask(@Body task: Task): Call<Task>

    @GET("{id}/")
    fun getTaskById(@Path("id") id: Long): Call<Task>







}