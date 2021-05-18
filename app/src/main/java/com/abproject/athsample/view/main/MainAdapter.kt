package com.abproject.athsample.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abproject.athsample.R
import com.abproject.athsample.data.dataclass.User
import com.abproject.athsample.util.DateConverter

/**
 * Created by Abolfazl on 5/16/21
 */
class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var users: MutableList<User> = mutableListOf()

    fun userDataChange(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun addUser(user: User) {
        users.add(0, user)
        notifyItemInserted(0)
    }


    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userFullName: TextView = itemView.findViewById(R.id.userFullNameItemTextView)
        private val userEmail: TextView = itemView.findViewById(R.id.userEmailItemTextView)
        private val userDate: TextView = itemView.findViewById(R.id.userDateItemTextView)

        fun bindUser(user: User) {
            userFullName.text = "${user.firstName} ${user.lastName}"
            userEmail.text = user.email
            userDate.text = DateConverter.convertStringToDateStringFormat(user.createAt)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindUser(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}