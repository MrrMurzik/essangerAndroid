package com.example.messengerAndroid.ui.contactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messengerAndroid.data.contactsRepository.UsersService
import com.example.messengerAndroid.data.contactsRepository.contactModel.UserWithState

class ViewDetailsViewModel(private val usersService: UsersService): ViewModel()  {

    private val _usersDetails = MutableLiveData<UserWithState>()
    val usersDetails: LiveData<UserWithState> = _usersDetails

    fun loadUser(id: String) {
         _usersDetails.value = usersService.usersList.firstOrNull() {it.user.id == id}
    }

}