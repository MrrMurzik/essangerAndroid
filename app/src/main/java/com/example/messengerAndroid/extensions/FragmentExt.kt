package com.example.messengerAndroid.extensions

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.example.messengerAndroid.ui.navigation.Navigator
import com.example.messengerAndroid.utils.Constants

fun Fragment.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts(Constants.SETTINGS_PACKAGE_SCHEME, requireActivity().packageName, null)
    intent.data = uri
    requireActivity().startActivity(intent)
}

fun Fragment.navigate(): Navigator {
    return requireActivity() as Navigator
}