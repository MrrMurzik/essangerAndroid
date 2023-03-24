package com.example.messengerAndroid.ui.viewPager.myContacts.contactsViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messengerAndroid.data.contactsRepository.UsersService
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.utils.UniqueIdGenerator.getUniqueId

data class MultiSelectUser (
    val user: User,
    val isSelected: Boolean
)

class ContactsViewModel(usersService: UsersService) : ViewModel() {
    
    private val _multiSelectLiveData = MutableLiveData<Boolean>()
    val multiSelectLiveData: LiveData<Boolean> = _multiSelectLiveData

    private val _contactsLiveData = MutableLiveData<List<User>>()
    val contactsLiveData: LiveData<List<User>> = _contactsLiveData

    private val _contactsSelectModeLiveData = MutableLiveData<List<MultiSelectUser>>()
    val contactsSelectModeLiveData: LiveData<List<MultiSelectUser>> = _contactsSelectModeLiveData

    init {
        _contactsLiveData.value = usersService.getUsers()
        _multiSelectLiveData.value = false
    }

    fun changeMode(position: Int) {

        if (multiSelectLiveData.value == false) {
            _multiSelectLiveData.value = true
            val list = mutableListOf<MultiSelectUser>()
            contactsLiveData.value?.forEach {
                list.add(MultiSelectUser(it, false))
            }
            _contactsSelectModeLiveData.value = list.toList()
            selectUser(position)
        } else {
            _multiSelectLiveData.value = true
            val list = mutableListOf<User>()
            contactsSelectModeLiveData.value?.forEach {
                list.add(it.user)
            }
            _contactsLiveData.value = list.toList()
        }

    }

    fun selectUser(position: Int) {
        _contactsSelectModeLiveData.value = _contactsSelectModeLiveData.value?.toMutableList()?.apply {
            this[position] = MultiSelectUser(this[position].user, true)
        }
    }

    fun unSelectUser(position: Int) {
        _contactsSelectModeLiveData.value = _contactsSelectModeLiveData.value?.toMutableList()?.apply {
            this[position] = MultiSelectUser(this[position].user, false)
        }
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