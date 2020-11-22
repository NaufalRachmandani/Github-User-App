package com.example.githubuserapp.model

import com.google.gson.annotations.SerializedName

data class ItemsResponse(

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null
)