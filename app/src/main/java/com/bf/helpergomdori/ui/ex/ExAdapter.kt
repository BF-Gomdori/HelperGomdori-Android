package com.bf.helpergomdori.ui.ex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bf.helpergomdori.R
import com.bf.helpergomdori.databinding.ItemExBinding
import com.bf.helpergomdori.model.User
import com.bumptech.glide.Glide


class ExAdapter(
    private val users: ArrayList<User>
) : RecyclerView.Adapter<ExAdapter.DataViewHolder>() {

    inner class DataViewHolder(private val binding: ItemExBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {

            textViewUserName.text = user.first_name + " " + user.last_name
            textViewUserEmail.text = user.email
            Glide.with(imageViewAvatar.context)
                .load(user.avatar)
                .into(imageViewAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
           DataBindingUtil.inflate(
               LayoutInflater.from(parent.context),
               R.layout.item_ex,
               parent, false
           )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

    fun addData(list: List<User>) {
        users.addAll(list)
    }

}