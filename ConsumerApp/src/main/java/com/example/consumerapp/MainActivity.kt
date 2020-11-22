package com.example.consumerapp

import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val AUTHORITY = "com.example.githubuserapp"
    private val CONTENT_URI: Uri = Uri.parse(
        "content://$AUTHORITY/user_table"
    )

    private var list = mutableListOf<UserResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initiateData()
        initiateUI()
    }

    private fun initiateData() {
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {

            }
        }
        loadUser()

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
    }

    private fun initiateUI() {
        showRecyclerList()
    }

    private fun loadUser() {
        val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
        cursor?.apply {
            while (moveToNext()) {
                val bio = getString(getColumnIndexOrThrow("bio"))
                val login = getString(getColumnIndexOrThrow("login"))
                val company = getString(getColumnIndexOrThrow("company"))
                val id = getInt(getColumnIndexOrThrow("id"))
                val publicRepos = getInt(getColumnIndexOrThrow("publicRepos"))
                val email = getString(getColumnIndexOrThrow("email"))
                val followers = getInt(getColumnIndexOrThrow("followers"))
                val avatarUrl = getString(getColumnIndexOrThrow("avatarUrl"))
                val following = getInt(getColumnIndexOrThrow("following"))
                val name = getString(getColumnIndexOrThrow("name"))
                val location = getString(getColumnIndexOrThrow("location"))

                list.add(UserResponse(
                    bio,
                    login,
                    company,
                    id,
                    publicRepos,
                    email,
                    followers,
                    avatarUrl,
                    following,
                    name,
                    location
                ))
            }
        }
        cursor?.close()
    }

    private fun showRecyclerList() {
        rv_user_consumerapp.layoutManager = LinearLayoutManager(this)
        rv_user_consumerapp.setHasFixedSize(true)
        rv_user_consumerapp.adapter = FavoriteAdapter(list)
    }
}