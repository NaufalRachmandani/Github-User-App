package com.example.githubuserapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.FavoriteAdapter
import com.example.githubuserapp.listener.OnListItemClickListener
import com.example.githubuserapp.model.UserResponse
import com.example.githubuserapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_favorite.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import java.util.*

class FavoriteActivity : AppCompatActivity(), OnListItemClickListener, View.OnClickListener {

    private lateinit var userViewModel: UserViewModel
    private lateinit var list: MutableList<UserResponse>
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        initiateData()
        initiateUI()
        initiateListener()
    }

    private fun initiateUI() {
        rv_user_favorite.layoutManager = LinearLayoutManager(this)
        rv_user_favorite.setHasFixedSize(true)

        et_search_favorite.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }
        })
    }

    private fun initiateData() {
        userViewModel = ViewModelProvider(
            this
        ).get(UserViewModel::class.java)

        //read data
        userViewModel.readAllData.observe(this, {
            showRecyclerList(it)
        })

        list = userViewModel.readAllData.value ?: mutableListOf()
    }

    private fun initiateListener() {
        btn_delete_all.setOnClickListener(this)
        btn_bacK_fav.setOnClickListener(this)
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

    private fun showRecyclerList(list: MutableList<UserResponse>) {
        adapter = FavoriteAdapter(list, this)
        rv_user_favorite.adapter = adapter
    }

    override fun onUserClick(username: String?) {
        startActivity(intentFor<DetailUserActivity>(DetailUserActivity.EXTRA_USERNAME to username))
    }

    private fun deleteAllUser() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete all favorite user?")
            .setPositiveButton("Yes") { dialog, id ->
                userViewModel.deleteAllUsers()
                toast("Delete Success")
                showRecyclerList(list)
            }
            .setNegativeButton("No") { dialog, id ->

            }
        builder.create().show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_delete_all -> {
                deleteAllUser()
            }
            R.id.btn_bacK_fav -> {
                onBackPressed()
            }
        }
    }

}