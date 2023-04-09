package com.example.messengerAndroid.app.ui.viewPager.myContacts.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.messengerAndroid.app.data.model.ContactWithState

class UserItemDiffCallback : DiffUtil.ItemCallback<ContactWithState>() {
    //    override fun areItemsTheSame(oldItem: ContactWithState, newItem: ContactWithState): Boolean =
//        oldItem.user.id == newItem.user.id && oldItem.isChecked == newItem.isChecked
//
//    override fun areContentsTheSame(oldItem: ContactWithState, newItem: ContactWithState): Boolean =
//        oldItem == newItem
    override fun areItemsTheSame(oldItem: ContactWithState, newItem: ContactWithState): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: ContactWithState, newItem: ContactWithState): Boolean {
        TODO("Not yet implemented")
    }

}