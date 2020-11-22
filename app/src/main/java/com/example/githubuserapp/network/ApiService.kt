package com.example.githubuserapp.network

import com.example.githubuserapp.model.ItemsResponse
import com.example.githubuserapp.model.ResponseSearchUser
import com.example.githubuserapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getListUsers(@Query("q") username: String?): Call<ResponseSearchUser>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String?): Call<UserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String?): Call<MutableList<ItemsResponse?>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String?): Call<MutableList<ItemsResponse?>>
}