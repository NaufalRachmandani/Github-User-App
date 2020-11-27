package com.example.consumerapp

import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val AUTHORITY = "com.example.githubuserapp"
    private val CONTENT_URI: Uri = Uri.parse(
        "content://$AUTHORITY/user_table"
    )

    private var list = mutableListOf<UserResponse>()
    private lateinit var adapter: FavoriteAdapter

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
        supportActionBar?.title = "Favorite User"
        showRecyclerList()

        et_search_favorite.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.isNullOrEmpty() || p0.isNullOrBlank() || p0.toString() == "") {
                    showRecyclerList()
                } else {
                    filter(p0.toString())
                }
            }
        })
    }

    private fun filter(text: String) {
        val filteredList = mutableListOf<UserResponse>()

        for (user in list) {
            if (user.login?.toLowerCase(Locale.ROOT)
                    ?.contains(text.toLowerCase(Locale.ROOT)) == true
            ) {
                filteredList.add(user)
            }
        }

        adapter.filter(filteredList)
    }

    private fun showRecyclerList() {
        rv_user_consumerapp.layoutManager = LinearLayoutManager(this)
        rv_user_consumerapp.setHasFixedSize(true)
        adapter = FavoriteAdapter(list)
        rv_user_consumerapp.adapter = adapter
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

                list.add(
                    UserResponse(
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
                    )
                )
            }
        }
        cursor?.close()
    }
}