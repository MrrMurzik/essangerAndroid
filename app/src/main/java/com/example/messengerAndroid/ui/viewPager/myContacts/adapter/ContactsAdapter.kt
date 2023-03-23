package com.example.messengerAndroid.ui.viewPager.myContacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.databinding.ItemContactDefaultBinding
import com.example.messengerAndroid.databinding.ItemContactSelectionModeBinding
import com.example.messengerAndroid.extensions.addPhoto

class ContactsAdapter (private val actionListener: UserActionListener)
    : ListAdapter<User, RecyclerView.ViewHolder>(UserItemDiffCallback()) {

    var isMultiselectMode = false
    val checkedUsers = mutableListOf<User>()


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isMultiselectMode) {
            val newHolder = holder as ContactsViewHolderSelectMode
            newHolder.bindTo(getItem(position))
        } else {
            val newHolder = holder as ContactsViewHolderDefault
            newHolder.bindTo(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (isMultiselectMode) {
            val binding = ItemContactSelectionModeBinding.inflate(inflater, parent, false)
            ContactsViewHolderSelectMode(binding)
        } else {
            val binding = ItemContactDefaultBinding.inflate(inflater, parent, false)
            ContactsViewHolderDefault(binding)
        }
    }


    inner class ContactsViewHolderDefault (private val binding: ItemContactDefaultBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bindTo(user: User) {
            with (binding) {
                tvName.text = user.name
                tvPhone.text = user.phone
                binding.ivAvatar.addPhoto(user.photo)
                setListeners(user)
            }
        }

        private fun setListeners(user: User) {
            binding.ibTrash.setOnClickListener {
                actionListener.onUserDelete(user)
            }

            binding.root.setOnClickListener {
                actionListener.onItemClicked(user)
            }

            binding.root.setOnLongClickListener {
                isMultiselectMode = !isMultiselectMode
                actionListener.updateRecycler()
                true
            }
        }
    }

    inner class ContactsViewHolderSelectMode (private val binding: ItemContactSelectionModeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(user: User) {
            with (binding) {
                tvName.text = user.name
                tvPhone.text = user.phone
                binding.ivAvatar.addPhoto(user.photo)
                setListeners(user)
            }
        }

        private fun setListeners(user: User) {

            binding.root.setOnClickListener {
                binding.checkBoxSelected.isChecked = !binding.checkBoxSelected.isChecked
                if(binding.checkBoxSelected.isChecked) {
                    checkedUsers.add(user)
                } else {
                    checkedUsers.remove(user)
                }
            }
            binding.root.setOnLongClickListener {
                isMultiselectMode = !isMultiselectMode
                checkedUsers.clear()
                actionListener.updateRecycler()
                true
            }
        }
    }


}