package com.example.githubuserapp.network

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRepository {
    companion object {

        fun getApiService(ctx: Context): ApiService {
            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "token 2f27b4a40aae53dafded7e0cde8dc48f2d218904")
                    .addHeader("User-Agent", "request")
                    .build()
                chain.proceed(newRequest)
            }.addInterceptor(ChuckInterceptor(ctx)).build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}