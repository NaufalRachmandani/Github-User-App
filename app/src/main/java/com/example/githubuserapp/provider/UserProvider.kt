package com.example.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.RoomMasterTable.TABLE_NAME
import com.example.githubuserapp.dao.UserDao
import com.example.githubuserapp.db.UserDatabase

class UserProvider : ContentProvider() {

    companion object {

        /** The authority of this content provider.  */
        private const val AUTHORITY = "com.example.githubuserapp"
        /** The URI for the user_table.  */
        val CONTENT_URI: Uri = Uri.parse(
            "content://$AUTHORITY/user_table"
        )
        private var userDao: UserDao? = null
        private const val USER = 1
        private const val USER_ID = 2
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(
                AUTHORITY,
                "user_table",
                USER
            )
            uriMatcher.addURI(AUTHORITY,
                "user_table/#",
                USER_ID)
        }
    }

    override fun onCreate(): Boolean {
        userDao = context?.let { UserDatabase.getDatabase(it).userDao() }
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return when (uriMatcher.match(uri)) {
            USER -> userDao?.readRawAllUser()
            USER_ID -> userDao?.readUserById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long? = when (USER) {
            /*uriMatcher.match(uri) -> values?.let { userDao?.addRawUser(it) }*/
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        //no need update, just insert and delete
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (USER_ID) {
           /* uriMatcher.match(uri) -> userDao?.deleteUserById(uri.lastPathSegment.toString()) ?: 0*/
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

}