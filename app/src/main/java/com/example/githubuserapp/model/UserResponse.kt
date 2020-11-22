package com.example.githubuserapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class UserResponse(
    @field:SerializedName("bio")
    val bio: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("public_repos")
    val publicRepos: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null
) : Parcelable