package com.example.githubuserapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.UserDetailPager
import com.example.githubuserapp.model.UserResponse
import com.example.githubuserapp.utils.GlideApp
import com.example.githubuserapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_detail_user.*

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    companion object {

        const val EXTRA_USERNAME = ""
    }

    private lateinit var userDetailPager: UserDetailPager
    private lateinit var userViewModel: UserViewModel
    private lateinit var username: String
    private var user: UserResponse? = null
    private var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        initiateData()
        initiateUI()
    }

    private fun initiateUI() {
        progressBar2.visibility = View.VISIBLE

        userDetailPager = UserDetailPager(this, supportFragmentManager)
        userDetailPager.username = username
        viewpager.adapter = userDetailPager
        tabs.setupWithViewPager(viewpager)

        btn_try_user.setOnClickListener(this)
        btn_back_detail_user.setOnClickListener(this)
        fab_detail_user.setOnClickListener(this)
    }

    private fun initiateData() {
        username = intent.getStringExtra(EXTRA_USERNAME).toString()

        userViewModel = ViewModelProvider(
            this
        ).get(UserViewModel::class.java)
        userViewModel.loadUser(username)

        userViewModel.user.observe(this, {
            progressBar2.visibility = View.GONE

            GlideApp.with(this)
                .load(it?.avatarUrl)
                .placeholder(R.drawable.icon_avatar)
                .into(civ_user_detail)
            tv_username_detail.text = username
            container_name_detail.text = it?.name
            container_location_detail.text = it?.location
            container_repository_detail.text = it?.publicRepos.toString()
            container_company_detail.text = it?.company
            container_followers_detail.text = it?.followers.toString()
            container_following_detail.text = it?.following.toString()

            if (container_name_detail.text == it?.name) {
                user = it
            }

            fab_detail_user.isEnabled = true
        })

        userViewModel.readAllData.observe(this, {
            for (position in it.indices) {
                if (username == it[position].login) {
                    isFav = true
                    isFavorite(isFav)
                }
            }
        })

        userViewModel.response.observe(this, {
            progressBar2.visibility = View.GONE
            gp_no_list_followers.visibility = View.VISIBLE
            tv_no_list_user.text = it.toString()
        })
    }

    private fun isFavorite(isFav: Boolean) {
        if (isFav) {
            fab_detail_user.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            fab_detail_user.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_try_user -> {
                progressBar2.visibility = View.VISIBLE
                userViewModel.loadUser(username)
                gp_no_list_followers.visibility = View.GONE
            }
            R.id.btn_back_detail_user -> {
                onBackPressed()
            }
            R.id.fab_detail_user -> {
                isFav = !isFav
                if (isFav) {
                    user?.let { userViewModel.addUser(it) }
                } else {
                    user?.let { userViewModel.deleteUser(it) }
                }
                isFavorite(isFav)
            }
        }
    }
}