package com.example.level2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.level2.model.User
import com.example.level2.dataBase.UsersDataBase

class ContactsViewModel : ViewModel() {

    private val _contactsLiveData = MutableLiveData<List<User>>()
    val contactsLiveData: LiveData<List<User>> = _contactsLiveData

    private val db = UsersDataBase()

    init {
        _contactsLiveData.value = db.getUsers()
    }

    fun deleteUser(user: User) {
        _contactsLiveData.value = _contactsLiveData.value?.toMutableList()?.apply {
            remove(user)
        }
    }

    fun addUser(user: User, position: Int) {
        _contactsLiveData.value = _contactsLiveData.value?.toMutableList()?.apply {
            add(position, user)
        }
    }

    fun getUserPosition(user: User): Int {
        return _contactsLiveData.value?.indexOf(user) ?: -1
    }
}