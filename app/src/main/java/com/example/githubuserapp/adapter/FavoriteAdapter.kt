package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.listener.OnListItemClickListener
import com.example.githubuserapp.model.UserResponse
import com.example.githubuserapp.utils.GlideApp
import kotlinx.android.synthetic.main.item_row_user.view.*

class FavoriteAdapter(
    private var list: MutableList<UserResponse>?,
    private val onListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list?.get(position))
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            user: UserResponse?
        ) {
            with(itemView) {
                GlideApp.with(this)
                    .load(user?.avatarUrl)
                    .placeholder(R.drawable.icon_avatar)
                    .into(civ_user)

                tv_username.text = user?.login

                itemView.setOnClickListener {
                    user?.let { onListItemClickListener.onUserClick(user.login) }
                }
            }
        }
    }

    fun filter(filteredList: MutableList<UserResponse>) {
        list = filteredList
        notifyDataSetChanged()
    }
}