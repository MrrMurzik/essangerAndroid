package com.example.messengerAndroid.ui.viewPager.myContacts.contactsViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messengerAndroid.data.contactsRepository.UsersService
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.data.contactsRepository.contactModel.UserWithState
import com.example.messengerAndroid.foundation.utils.UniqueIdGenerator.getUniqueId


class ContactsViewModel(private val usersService: UsersService) : ViewModel() {


    private val _contactsLiveData = MutableLiveData<List<UserWithState>>()
    val contactsLiveData: LiveData<List<UserWithState>> = _contactsLiveData

    private val _noResultLiveData = MutableLiveData(false)
    val noResultLiveData: LiveData<Boolean> = _noResultLiveData


    init {
        _contactsLiveData.value = usersService.getUsers()
    }

    fun deleteUser(userWithState: UserWithState?) {
        _contactsLiveData.value = _contactsLiveData.value?.toMutableList()?.apply {
            remove(userWithState)
        }
    }

    fun addExistingUser(user: User?, position: Int) {
        _contactsLiveData.value = _contactsLiveData.value?.toMutableList()?.apply {
            if (user != null) {
                add(position, UserWithState(user, isMultiselectMode = false, isChecked = false))
            }
        }
    }

    fun addNewUser(name: String, job: String, photo: String, phone: String, address: String) {
        _contactsLiveData.value = _contactsLiveData.value?.plus(
            UserWithState(
                User(
                    getUniqueId(),
                    photo,
                    name,
                    job,
                    phone,
                    address
                ),
                isMultiselectMode = false,
                isChecked = false
            )
        )
    }

    fun getUserPosition(userWithState: UserWithState): Int {
        return contactsLiveData.value?.indexOf(userWithState) ?: -1
    }

    fun changeMode() {
        _contactsLiveData.value = _contactsLiveData.value?.map {
            if (!it.isMultiselectMode)
                UserWithState(it.user, isMultiselectMode = true, isChecked = false)
            else
                UserWithState(it.user, isMultiselectMode = false, isChecked = false)
        }
    }

    fun chooseUserChecked(userWithState: UserWithState) {
        _contactsLiveData.value = _contactsLiveData.value?.map {
            if (userWithState == it) {
                if (it.isChecked) {
                    UserWithState(it.user, isMultiselectMode = true, isChecked = false)
                } else {
                    UserWithState(it.user, isMultiselectMode = true, isChecked = true)
                }
            } else {
                it
            }
        }
    }


    fun deleteCheckedUsers() {
        _contactsLiveData.value = _contactsLiveData.value?.filter { !it.isChecked }
            ?.map { UserWithState(it.user, isMultiselectMode = false, isChecked = false) }
    }

    fun getCurrentMode(): Boolean {
        if (contactsLiveData.value?.size == 0) return false
        return contactsLiveData.value?.get(0)?.isMultiselectMode ?: false
    }
    fun selectAllUsers() {
        _contactsLiveData.value = _contactsLiveData.value?.map {
            UserWithState(it.user, isMultiselectMode = true, isChecked = true)
        }
    }


    fun searchInList(input: String) {
        _contactsLiveData.value = _contactsLiveData.value?.filter {
            it.user.name.contains(input)
        }
        _noResultLiveData.value = _contactsLiveData.value?.isEmpty() == true
    }

    fun enableDefaultMode() {
        _contactsLiveData.value = usersService.getUsers()
        _noResultLiveData.value = false
    }


}