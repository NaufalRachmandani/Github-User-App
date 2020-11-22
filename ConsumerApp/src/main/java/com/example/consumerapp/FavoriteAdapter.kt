package com.example.consumerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_row_user.view.*

class FavoriteAdapter(
    private var list: MutableList<UserResponse>?
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
                Glide.with(this)
                    .load(user?.avatarUrl)
                    .into(civ_user)

                tv_username.text = user?.login
            }
        }
    }

}