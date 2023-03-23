package com.example.messengerAndroid.ui.contactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messengerAndroid.data.contactsRepository.UsersService
import com.example.messengerAndroid.data.contactsRepository.contactModel.User

class ViewDetailsViewModel(private val usersService: UsersService): ViewModel()  {

    private val _usersDetails = MutableLiveData<User>()
    val usersDetails: LiveData<User> = _usersDetails

    fun loadUser(id: String) {
         _usersDetails.value = usersService.usersList.firstOrNull() {it.id == id}
    }

}