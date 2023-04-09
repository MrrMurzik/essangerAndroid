package com.example.messengerAndroid.app.ui.viewPager.myContacts.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerAndroid.app.data.model.ContactWithState
import com.example.messengerAndroid.databinding.ItemContactBinding
import com.example.messengerAndroid.app.foundation.extensions.addPhoto

class ContactsAdapter (private val actionListener: UserActionListener,
                       private val backgroundColorSelector: BackgroundColorSelector
)
    : ListAdapter<ContactWithState, ContactsAdapter.ContactsViewHolder>(UserItemDiffCallback()) {



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

        fun bindTo(contactWithState: ContactWithState) {
            //todo
//            with (binding) {
//                tvName.text = contactWithState.user.name
//                tvPhone.text = contactWithState.user.phone
//                ivAvatar.addPhoto(contactWithState.user.photo)
//                if (contactWithState.isMultiselectMode) {
//                    checkBoxSelected.visibility = VISIBLE
//                    checkBoxSelected.isChecked = contactWithState.isChecked
//                    ibTrash.visibility = GONE
//                    root.background = backgroundColorSelector.setBackgroundSelectionDrawable()
//                    setListenersForMultiselectMode(contactWithState)
//                } else {
//                    root.background = backgroundColorSelector.setBackgroundDefaultDrawable()
//                    checkBoxSelected.visibility = GONE
//                    ibTrash.visibility = VISIBLE
//                    setListenersForDefaultMode(contactWithState)
//                }
//            }

        }

        private fun setListenersForDefaultMode(contactWithState: ContactWithState) {
            binding.ibTrash.setOnClickListener {
                actionListener.onUserDelete(contactWithState)
            }

            binding.root.setOnClickListener {
                actionListener.onItemClicked(contactWithState)
            }

            binding.root.setOnLongClickListener {
                actionListener.onChangeMode()
                true
            }
        }

        private fun setListenersForMultiselectMode(contactWithState: ContactWithState) {

            binding.root.setOnClickListener {
                actionListener.onItemClickedChooseMode(contactWithState)
            }

            binding.root.setOnLongClickListener {
                actionListener.onChangeMode()
                true
            }

            binding.checkBoxSelected.setOnClickListener {
                actionListener.onItemClickedChooseMode(contactWithState)
            }
        }
    }
}