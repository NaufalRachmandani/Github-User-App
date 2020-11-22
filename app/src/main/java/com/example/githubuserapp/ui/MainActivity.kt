package com.example.githubuserapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.listener.OnListItemClickListener
import com.example.githubuserapp.model.ItemsResponse
import com.example.githubuserapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity(), OnListItemClickListener, View.OnClickListener {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initiateData()
        initiateUI()
        initiateListener()
    }

    private fun initiateUI() {
        progressBar.visibility = VISIBLE

        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.setHasFixedSize(true)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                progressBar.visibility = VISIBLE
                if (p0.isNullOrEmpty() || p0.isNullOrBlank() || p0.toString() == "") {
                    userViewModel.loadSearchUser("a")
                } else {
                    userViewModel.loadSearchUser(p0.toString().trim())
                }
            }
        })
    }

    private fun initiateData() {
        userViewModel = ViewModelProvider(
            this
        ).get(UserViewModel::class.java)
        userViewModel.loadSearchUser("a")

        userViewModel.listUsers.observe(this, {
            progressBar.visibility = GONE
            showRecyclerList(it)
        })

        userViewModel.response.observe(this, {
            progressBar.visibility = GONE
            showRecyclerList(mutableListOf())
            gp_no_list_user.visibility = VISIBLE
            tv_no_list_user.text = it.toString()
        })
    }

    private fun initiateListener() {
        btn_try.setOnClickListener(this)
        btn_favorite.setOnClickListener(this)
        btn_setting.setOnClickListener(this)
    }

    private fun showRecyclerList(list: MutableList<ItemsResponse?>?) {
        rv_user.adapter = UserAdapter(list, this)
    }

    override fun onUserClick(username: String?) {
        startActivity(intentFor<DetailUserActivity>(DetailUserActivity.EXTRA_USERNAME to username))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_try -> {
                progressBar.visibility = VISIBLE
                userViewModel.loadSearchUser("a")
                gp_no_list_user.visibility = GONE
            }
            R.id.btn_favorite -> {
                startActivity(intentFor<FavoriteActivity>())
            }
            R.id.btn_setting -> {
                startActivity(intentFor<SettingActivity>())
            }
        }
    }
}