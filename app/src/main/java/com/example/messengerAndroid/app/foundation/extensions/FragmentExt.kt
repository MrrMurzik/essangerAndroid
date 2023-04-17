package com.example.messengerAndroid.app.foundation.extensions

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.example.messengerAndroid.app.App
import com.example.messengerAndroid.app.ui.navigation.Navigator
import com.example.messengerAndroid.app.Constants
import com.example.messengerAndroid.app.foundation.viewModelFactory.ViewModelFactory

fun Fragment.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts(Constants.SETTINGS_PACKAGE_SCHEME, requireActivity().packageName, null)
    intent.data = uri
    requireActivity().startActivity(intent)
}

fun Fragment.navigate(): Navigator {
    return requireActivity() as Navigator
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)