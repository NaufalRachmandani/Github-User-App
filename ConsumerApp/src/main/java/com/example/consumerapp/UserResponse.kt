package com.example.consumerapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserResponse(
    val bio: String? = null,
    val login: String? = null,
    val company: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val publicRepos: Int? = null,
    val email: String? = null,
    val followers: Int? = null,
    val avatarUrl: String? = null,
    val following: Int? = null,
    val name: String? = null,
    val location: String? = null
)