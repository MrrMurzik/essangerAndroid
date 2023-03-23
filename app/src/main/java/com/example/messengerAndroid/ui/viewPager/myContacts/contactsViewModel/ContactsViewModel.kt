package com.example.messengerAndroid.ui.viewPager.myContacts.contactsViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messengerAndroid.data.contactsRepository.UsersService
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.utils.UniqueIdGenerator.getUniqueId


class ContactsViewModel(usersService: UsersService) : ViewModel() {

    private val _contactsLiveData = MutableLiveData<List<User>>()
    val contactsLiveData: LiveData<List<User>> = _contactsLiveData


    init {
        _contactsLiveData.value = usersService.getUsers()
    }

    fun deleteUser(user: User?) {
        _contactsLiveData.value = _contactsLiveData.value?.toMutableList()?.apply {
            remove(user)
        }
    }

    fun addExistingUser(user: User?, position: Int) {
        _contactsLiveData.value = _contactsLiveData.value?.toMutableList()?.apply {
            if (user != null) {
                add(position, user)
            }
        }
    }

    fun addNewUser(name: String, job: String, photo: String, phone: String, address: String) {
        _contactsLiveData.value = _contactsLiveData.value?.plus(
            User(getUniqueId(),
            photo,
            name,
            job,
            phone,
            address)
        )
    }

    fun getUserPosition(user: User): Int {
        return contactsLiveData.value?.indexOf(user) ?: -1
    }




}