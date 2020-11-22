package com.example.githubuserapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.listener.OnListItemClickListener
import com.example.githubuserapp.model.ItemsResponse
import com.example.githubuserapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_followers.*
import org.jetbrains.anko.intentFor

class FollowersFragment : Fragment(), OnListItemClickListener, View.OnClickListener {

    companion object {

        private const val EXTRA_USERNAME = "username"
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int, username: String): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_USERNAME, username)
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var userViewModel: UserViewModel
    private lateinit var username: String
    private var index = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateUI()
        initiateData()
    }

    private fun initiateUI() {
        progressBar3.visibility = VISIBLE
        btn_try_followers.setOnClickListener(this)
    }

    private fun initiateData() {
        if (arguments != null) {
            index = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
        }

        userViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)

        username = arguments?.getString(EXTRA_USERNAME).toString()

        if (index == 0) {
            userViewModel.loadFollowers(username)

            userViewModel.listFollowers.observe(viewLifecycleOwner, {
                progressBar3.visibility = GONE

                if (it != null) {
                    showRecyclerList(it)
                }
            })
        } else {
            userViewModel.loadFollowing(username)

            userViewModel.listFollowing.observe(viewLifecycleOwner, {
                progressBar3.visibility = GONE

                if (it != null) {
                    showRecyclerList(it)
                }
            })
        }

        userViewModel.response.observe(viewLifecycleOwner, {
            progressBar3.visibility = GONE
            gp_no_list_followers.visibility = VISIBLE
            tv_no_list_user_followers.text = it.toString()
        })
    }

    private fun showRecyclerList(list: MutableList<ItemsResponse?>) {
        rv_followers_user.setHasFixedSize(true)
        rv_followers_user.layoutManager = LinearLayoutManager(context)
        val adapter = UserAdapter(list, this)
        rv_followers_user.adapter = adapter
    }

    override fun onUserClick(username: String?) {
        startActivity(context?.intentFor<DetailUserActivity>(DetailUserActivity.EXTRA_USERNAME to username))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_try_followers -> {
                progressBar3.visibility = VISIBLE
                if (index == 0) {
                    userViewModel.loadFollowers(username)
                } else {
                    userViewModel.loadFollowing(username)
                }
                gp_no_list_followers.visibility = GONE
            }
        }
    }

}