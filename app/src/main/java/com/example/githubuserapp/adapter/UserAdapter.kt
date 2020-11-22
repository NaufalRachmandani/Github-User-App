package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.listener.OnListItemClickListener
import com.example.githubuserapp.model.ItemsResponse
import com.example.githubuserapp.utils.GlideApp
import kotlinx.android.synthetic.main.item_row_user.view.*

class UserAdapter(
    private val list: MutableList<ItemsResponse?>?,
    private val onListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

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
            user: ItemsResponse?
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
}