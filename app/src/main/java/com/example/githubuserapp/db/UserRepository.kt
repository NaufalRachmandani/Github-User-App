package com.example.githubuserapp.db

import androidx.lifecycle.LiveData
import com.example.githubuserapp.dao.UserDao
import com.example.githubuserapp.model.UserResponse

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<MutableList<UserResponse>> = userDao.readAllUser()

    suspend fun addUser(userResponse: UserResponse) {
        userDao.addUser(userResponse)
    }

    suspend fun deleteUser(userResponse: UserResponse) {
        userDao.deleteUser(userResponse)
    }

    suspend fun deleteAllUser() {
        userDao.deleteAllUsers()
    }
}