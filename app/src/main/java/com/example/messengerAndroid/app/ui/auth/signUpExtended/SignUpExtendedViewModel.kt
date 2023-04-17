package com.example.messengerAndroid.app.ui.auth.signUpExtended

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.messengerAndroid.app.data.model.user.UserRepository
import com.example.messengerAndroid.app.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.app.data.result.Result
import com.example.messengerAndroid.app.data.result.SuccessResult
import com.example.messengerAndroid.app.data.result.SystemErrorResult
import com.example.messengerAndroid.app.foundation.exceptions.BackendException
import com.example.messengerAndroid.app.foundation.exceptions.ConnectionException
import com.example.messengerAndroid.app.foundation.exceptions.ParseBackendResponseException
import com.example.messengerAndroid.app.ui.auth.signUp.Error
import com.example.messengerAndroid.source.user.entities.EditUserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpExtendedViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _resultLiveData = MutableLiveData<Result<NavDirections>>()
    val resultLiveData: LiveData<Result<NavDirections>> = _resultLiveData

    fun registerNameAndPhone(name: String, phone: String) = viewModelScope.launch {
        val photoUri = SharedPreferencesHelper.getPhoto()
        try {
            val userData = userRepository.editUser(name, phone, photoUri)
            saveUserData(userData)
            _resultLiveData.value =
                SuccessResult(SignUpExtendedFragmentDirections.actionSignUpExtendedFragmentToViewPagerFragment())
        } catch (e: ConnectionException) {
            _resultLiveData.value = SystemErrorResult(Error.CONNECTION_EXCEPTION)
        } catch (e: BackendException) {
            _resultLiveData.value = SystemErrorResult(Error.CONNECTION_EXCEPTION)
        } catch (e: ParseBackendResponseException) {
            _resultLiveData.value = SystemErrorResult(Error.PARSE_BACKEND_EXCEPTION)
        }
    }

    private fun saveUserData(userData: EditUserEntity) {
        SharedPreferencesHelper.setName(userData.name?: "")
    }


    fun saveUserPhoto(photoUri: String) {
        SharedPreferencesHelper.setPhoto(photoUri)
    }

}