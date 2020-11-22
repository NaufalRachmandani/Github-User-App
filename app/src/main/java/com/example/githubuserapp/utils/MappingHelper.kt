package com.example.githubuserapp.utils

import android.database.Cursor
import com.example.githubuserapp.db.UserDatabase
import com.example.githubuserapp.model.UserResponse

object MappingHelper {

    fun mapCursorToArrayList(userCursor: Cursor?): MutableList<UserResponse> {
        val userList = mutableListOf<UserResponse>()
        userCursor?.apply {
            while (moveToNext()) {
                /*val id = getInt(getColumnIndexOrThrow(""))
                userList.add(UserResponse(
                    id,
                    title,
                    description,
                    date
                ))*/
            }
        }
        return userList
    }
}