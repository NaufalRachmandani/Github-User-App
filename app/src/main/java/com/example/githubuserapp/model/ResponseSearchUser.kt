package com.example.githubuserapp.model

import com.google.gson.annotations.SerializedName

data class ResponseSearchUser(
    @SerializedName("items")
    val responses: MutableList<ItemsResponse?>? = null
)