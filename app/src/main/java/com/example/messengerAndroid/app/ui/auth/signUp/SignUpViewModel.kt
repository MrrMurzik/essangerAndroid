package com.example.messengerAndroid.app.ui.auth.signUp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.messengerAndroid.app.data.model.user.UserRepository
import com.example.messengerAndroid.app.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.app.data.result.ActionErrorResult
import com.example.messengerAndroid.app.data.result.Result
import com.example.messengerAndroid.app.data.result.SuccessResult
import com.example.messengerAndroid.app.data.result.SystemErrorResult
import com.example.messengerAndroid.app.foundation.exceptions.*
import com.example.messengerAndroid.app.foundation.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _resultLiveData = MutableLiveData<Result<NavDirections>>()
    val resultLiveData: LiveData<Result<NavDirections>> = _resultLiveData





    fun registerEmailAndPassword(email: String, password: String, isChecked: Boolean) = viewModelScope.launch {
        if (checkInputValidity(email, password)) {
            try {
                val result = userRepository.createUser(email, password)
                if (result is SuccessResult) {
                    Log.d("myTag", "result.data.id: ${result.data}")
                    SharedPreferencesHelper.setToken(result.data.data.accessToken)
                    Log.d("myTag", "result.data.accessToken: ${result.data.data.accessToken}")
                    Log.d("myTag", "result.data.id: ${result.data.data.user.id}")
                    SharedPreferencesHelper.setNetworkId(result.data.data.user.id.toString())
                }
                _resultLiveData.value =
                    SuccessResult(SignUpFragmentDirections.actionSignUpFragmentToSignUpExtendedFragment())
            } catch (e: AccountAlreadyExistsException) {
                _resultLiveData.value = ActionErrorResult(Error.ACCOUNT_ALREADY_EXIST)
            } catch (e: ConnectionException) {
                _resultLiveData.value = SystemErrorResult(Error.CONNECTION_EXCEPTION)
            } catch (e: BackendException) {
                _resultLiveData.value = SystemErrorResult(Error.CONNECTION_EXCEPTION)
            } catch (e: ParseBackendResponseException) {
                _resultLiveData.value = SystemErrorResult(Error.PARSE_BACKEND_EXCEPTION)
            }
0



        }
    }


    private fun checkInputValidity(email: String, password: String): Boolean {
        var result = true
        if (!Validator.getValidityEmail(email)) {
            _resultLiveData.value = ActionErrorResult(Error.INVALID_EMAIL)
            result = false
        }
        if (!Validator.getValidityPassword(password)) {
            _resultLiveData.value = ActionErrorResult(Error.INVALID_PASSWORD)
            result = false
        }
        return result
    }

}