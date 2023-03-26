package com.example.messengerAndroid.ui.viewPager.myContacts.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.messengerAndroid.data.contactsRepository.contactModel.UserWithState

class UserItemDiffCallback : DiffUtil.ItemCallback<UserWithState>() {
    override fun areItemsTheSame(oldItem: UserWithState, newItem: UserWithState): Boolean =
        oldItem.user.id == newItem.user.id && oldItem.isChecked == newItem.isChecked

    override fun areContentsTheSame(oldItem: UserWithState, newItem: UserWithState): Boolean =
        oldItem == newItem

}