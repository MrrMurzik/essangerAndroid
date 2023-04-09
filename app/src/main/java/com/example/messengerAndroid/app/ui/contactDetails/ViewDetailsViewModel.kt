package com.example.messengerAndroid.app.ui.contactDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messengerAndroid.app.data.UsersService
import com.example.messengerAndroid.app.data.model.ContactWithState

class ViewDetailsViewModel(private val usersService: UsersService): ViewModel()  {

    private val _usersDetails = MutableLiveData<ContactWithState>()
    val usersDetails: LiveData<ContactWithState> = _usersDetails

    fun loadUser(id: String) {
        //TODO
//         _usersDetails.value = usersService.usersList.firstOrNull() {it..id == id}
    }

}