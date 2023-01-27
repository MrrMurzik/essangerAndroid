package com.example.level2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.databinding.RecyclerItemBinding
import com.example.level2.extensions.addPhoto
import com.example.level2.model.User

interface UserActionListener {

    fun onUserDelete (user: User)

}


class UserItemDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}


class ContactsAdapter (private val actionListener: UserActionListener)
    : ListAdapter<User, ContactsAdapter.ContactsViewHolder>(UserItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemBinding.inflate(inflater, parent, false)
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class ContactsViewHolder (private val binding: RecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bindTo(user: User) {
            with (binding) {
                tvName.text = user.name
                tvJob.text = user.job
                binding.ivAvatar.addPhoto(user)
                setListeners(user)
            }
        }

        private fun setListeners(user: User) {
            binding.ibTrash.setOnClickListener {
                actionListener.onUserDelete(user)
            }

        }
    }

}