package com.example.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.db.UserDatabase
import com.example.githubuserapp.db.UserRepository
import com.example.githubuserapp.model.ItemsResponse
import com.example.githubuserapp.model.ResponseSearchUser
import com.example.githubuserapp.model.UserResponse
import com.example.githubuserapp.network.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val app: Application) : AndroidViewModel(app) {

    val listUsers = MutableLiveData<MutableList<ItemsResponse?>>()
    val user = MutableLiveData<UserResponse>()
    val listFollowers = MutableLiveData<MutableList<ItemsResponse?>>()
    val listFollowing = MutableLiveData<MutableList<ItemsResponse?>>()
    val response = MutableLiveData<String?>()

    val readAllData: LiveData<MutableList<UserResponse>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(app).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun loadSearchUser(username: String) {
        // Do an asynchronous operation to fetch users.

        val client = ApiRepository.getApiService(app).getListUsers(username)

        client.enqueue(object : Callback<ResponseSearchUser> {
            override fun onResponse(
                call: Call<ResponseSearchUser>,
                response: Response<ResponseSearchUser>
            ) {
                try {
                    val dataArray = response.body()?.responses

                    listUsers.postValue(dataArray)
                } catch (e: Exception) {
                    println("Error Message : " + e.message)
                }
            }

            override fun onFailure(call: Call<ResponseSearchUser>, t: Throwable) {
                response.postValue("failure : ${t.message}")
            }
        })
    }

    fun loadUser(username: String) {
        // Do an asynchronous operation to fetch users.

        val client = ApiRepository.getApiService(app).getUser(username)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                try {
                    val data = response.body()

                    user.postValue(data)
                } catch (e: Exception) {
                    println("Error Message : " + e.message)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                response.postValue("failure : ${t.message}")
            }
        })
    }

    fun loadFollowers(username: String) {
        // Do an asynchronous operation to fetch users.

        val client = ApiRepository.getApiService(app).getFollowers(username)

        client.enqueue(object : Callback<MutableList<ItemsResponse?>> {
            override fun onResponse(
                call: Call<MutableList<ItemsResponse?>>,
                response: Response<MutableList<ItemsResponse?>>
            ) {
                try {
                    val dataArray = response.body()

                    listFollowers.postValue(dataArray)
                } catch (e: Exception) {
                    println("Error Message : " + e.message)
                }
            }

            override fun onFailure(call: Call<MutableList<ItemsResponse?>>, t: Throwable) {
                response.postValue("failure followers : ${t.message}")
            }
        })
    }

    fun loadFollowing(username: String) {
        // Do an asynchronous operation to fetch users.

        val client = ApiRepository.getApiService(app).getFollowing(username)

        client.enqueue(object : Callback<MutableList<ItemsResponse?>> {
            override fun onResponse(
                call: Call<MutableList<ItemsResponse?>>,
                response: Response<MutableList<ItemsResponse?>>
            ) {
                try {
                    val dataArray = response.body()

                    listFollowing.postValue(dataArray)
                } catch (e: Exception) {
                    println("Error Message : " + e.message)
                }
            }

            override fun onFailure(call: Call<MutableList<ItemsResponse?>>, t: Throwable) {
                response.postValue("failure followers : ${t.message}")
            }
        })
    }

    fun addUser(userResponse: UserResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(userResponse)
        }
    }

    fun deleteUser(userResponse: UserResponse){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(userResponse)
        }
    }

    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUser()
        }
    }
}