package com.example.messengerAndroid.app.ui.viewPager.myContacts.contactsViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messengerAndroid.app.data.UsersService
import com.example.messengerAndroid.app.data.model.Contact
import com.example.messengerAndroid.app.data.model.ContactWithState
import com.example.messengerAndroid.app.foundation.utils.UniqueIdGenerator.getUniqueId


class ContactsViewModel(private val usersService: UsersService) : ViewModel() {


    private val _contactsLiveData = MutableLiveData<List<ContactWithState>>()
    val contactsLiveData: LiveData<List<ContactWithState>> = _contactsLiveData

    private val _noResultLiveData = MutableLiveData(false)
    val noResultLiveData: LiveData<Boolean> = _noResultLiveData


    init {
        _contactsLiveData.value = usersService.getUsers()
    }

    fun deleteUser(contactWithState: ContactWithState?) {
        _contactsLiveData.value = _contactsLiveData.value?.toMutableList()?.apply {
            remove(contactWithState)
        }
    }

    fun addExistingUser(contact: Contact?, position: Int) {
        _contactsLiveData.value = _contactsLiveData.value?.toMutableList()?.apply {
            if (contact != null) {
                add(position, ContactWithState(contact, isMultiselectMode = false, isChecked = false))
            }
        }
    }

    fun addNewUser(name: String, job: String, photo: String, phone: String, address: String) {
        _contactsLiveData.value = _contactsLiveData.value?.plus(
            ContactWithState(
                Contact(
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

    fun getUserPosition(contactWithState: ContactWithState): Int {
        return contactsLiveData.value?.indexOf(contactWithState) ?: -1
    }

    fun changeMode() {
        _contactsLiveData.value = _contactsLiveData.value?.map {
            if (!it.isMultiselectMode)
                ContactWithState(it.contact, isMultiselectMode = true, isChecked = false)
            else
                ContactWithState(it.contact, isMultiselectMode = false, isChecked = false)
        }
    }

    fun chooseUserChecked(contactWithState: ContactWithState) {
        _contactsLiveData.value = _contactsLiveData.value?.map {
            if (contactWithState == it) {
                if (it.isChecked) {
                    ContactWithState(it.contact, isMultiselectMode = true, isChecked = false)
                } else {
                    ContactWithState(it.contact, isMultiselectMode = true, isChecked = true)
                }
            } else {
                it
            }
        }
    }


    fun deleteCheckedUsers() {
        _contactsLiveData.value = _contactsLiveData.value?.filter { !it.isChecked }
            ?.map { ContactWithState(it.contact, isMultiselectMode = false, isChecked = false) }
    }

    fun getCurrentMode(): Boolean {
        if (contactsLiveData.value?.size == 0) return false
        return contactsLiveData.value?.get(0)?.isMultiselectMode ?: false
    }
    fun selectAllUsers() {
        _contactsLiveData.value = _contactsLiveData.value?.map {
            ContactWithState(it.contact, isMultiselectMode = true, isChecked = true)
        }
    }


    fun searchInList(input: String) {
        _contactsLiveData.value = _contactsLiveData.value?.filter {
            it.contact.name.contains(input)
        }
        _noResultLiveData.value = _contactsLiveData.value?.isEmpty() == true
    }

    fun enableDefaultMode() {
        _contactsLiveData.value = usersService.getUsers()
        _noResultLiveData.value = false
    }


}