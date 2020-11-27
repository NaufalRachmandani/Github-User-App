package com.example.githubuserapp.ui

import android.os.Bundle
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

class FavoriteActivity : AppCompatActivity(), OnListItemClickListener, View.OnClickListener {

    private lateinit var userViewModel: UserViewModel
    private var list = mutableListOf<UserResponse>()
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
    }

    private fun initiateData() {
        userViewModel = ViewModelProvider(
            this
        ).get(UserViewModel::class.java)

        //read data
        userViewModel.readAllData.observe(this, {
            showRecyclerList(it)
        })

        userViewModel.readAllData.value?.let { list.addAll(it) }
    }

    private fun initiateListener() {
        btn_delete_all.setOnClickListener(this)
        btn_bacK_fav.setOnClickListener(this)
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
            .setPositiveButton("Yes") { _, _ ->
                userViewModel.deleteAllUsers()
                toast("Delete Success")
                showRecyclerList(list)
            }
            .setNegativeButton("No") { _, _ ->

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