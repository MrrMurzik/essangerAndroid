package com.example.messengerAndroid.ui.viewPager.myContacts.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerAndroid.data.contactsRepository.contactModel.UserWithState
import com.example.messengerAndroid.databinding.ItemContactBinding
import com.example.messengerAndroid.foundation.extensions.addPhoto

class ContactsAdapter (private val actionListener: UserActionListener,
private val backgroundColorSelector: BackgroundColorSelector)
    : ListAdapter<UserWithState, ContactsAdapter.ContactsViewHolder>(UserItemDiffCallback()) {



    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        return ContactsViewHolder(binding)
    }




    inner class ContactsViewHolder (private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bindTo(userWithState: UserWithState) {
            with (binding) {
                tvName.text = userWithState.user.name
                tvPhone.text = userWithState.user.phone
                ivAvatar.addPhoto(userWithState.user.photo)
                if (userWithState.isMultiselectMode) {
                    checkBoxSelected.visibility = VISIBLE
                    checkBoxSelected.isChecked = userWithState.isChecked
                    ibTrash.visibility = GONE
                    root.background = backgroundColorSelector.setBackgroundSelectionDrawable()
                    setListenersForMultiselectMode(userWithState)
                } else {
                    root.background = backgroundColorSelector.setBackgroundDefaultDrawable()
                    checkBoxSelected.visibility = GONE
                    ibTrash.visibility = VISIBLE
                    setListenersForDefaultMode(userWithState)
                }
            }

        }

        private fun setListenersForDefaultMode(userWithState: UserWithState) {
            binding.ibTrash.setOnClickListener {
                actionListener.onUserDelete(userWithState)
            }

            binding.root.setOnClickListener {
                actionListener.onItemClicked(userWithState)
            }

            binding.root.setOnLongClickListener {
                actionListener.onChangeMode()
                true
            }
        }

        private fun setListenersForMultiselectMode(userWithState: UserWithState) {

            binding.root.setOnClickListener {
                actionListener.onItemClickedChooseMode(userWithState)
            }

            binding.root.setOnLongClickListener {
                actionListener.onChangeMode()
                true
            }

            binding.checkBoxSelected.setOnClickListener {
                actionListener.onItemClickedChooseMode(userWithState)
            }
        }
    }
}