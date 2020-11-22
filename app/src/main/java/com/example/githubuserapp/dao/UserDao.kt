package com.example.githubuserapp.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapp.model.UserResponse

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userResponse: UserResponse)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllUser(): LiveData<MutableList<UserResponse>>

    @Delete
    suspend fun deleteUser(userResponse: UserResponse)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readRawAllUser(): Cursor

    @Query("SELECT * FROM user_table WHERE id=:id")
    fun readUserById(id: String): Cursor
}