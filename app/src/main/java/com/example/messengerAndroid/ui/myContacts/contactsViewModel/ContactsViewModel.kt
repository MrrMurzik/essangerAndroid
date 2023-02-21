package com.example.messengerAndroid.ui.myContacts.contactsViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.data.contactsRepository.UsersListGenerator

class ContactsViewModel : ViewModel() {

    private val _contactsLiveData = MutableLiveData<List<User>>()
    val contactsLiveData: LiveData<List<User>> = _contactsLiveData

    private val db = UsersListGenerator()

    init {
        _contactsLiveData.value = db.getUsers()
    }

    fun deleteUser(user: User) {
        _contactsLiveData.value = _contactsLiveData.value?.toMutableList()?.apply {
            remove(user)
        }
    }

    fun addExistingUser(user: User, position: Int) {
        _contactsLiveData.value = _contactsLiveData.value?.toMutableList()?.apply {
            add(position, user)
        }
    }

    fun addNewUser(name: String, job: String) {
        _contactsLiveData.value = _contactsLiveData.value?.toMutableList()?.apply {
            add(User(_contactsLiveData.value!!.size.toLong(), "", name, job))
        }
    }

    fun getUserPosition(user: User): Int {
        return contactsLiveData.value?.indexOf(user) ?: -1
    }

    fun getUser(position: Int): User? {
        return contactsLiveData.value?.toMutableList()?.get(position)
    }


}