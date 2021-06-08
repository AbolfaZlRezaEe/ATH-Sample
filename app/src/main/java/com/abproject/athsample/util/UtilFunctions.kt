package com.abproject.athsample.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Patterns
import com.abproject.athsample.ATHGlobal
import com.abproject.athsample.base.ATHViewModel
import com.abproject.athsample.util.checkinternetconnection.ConnectionLiveData

/**
 * Created by Abolfazl on 5/17/21
 */
fun CharSequence.checkEmailIsValid() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun validationIranianPhoneNumber(number: String): Boolean {
    val phoneNumberPattern =
        Regex("(0|\\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}")
    return (number.isNotEmpty() && number.matches(phoneNumberPattern))
}

/**
 * checkInternetConnection is a function that checks the device Connection
 * Mobile data or Wifi or Both and then @return false if no Connection in the Device.
 */
@Deprecated(
    "this Method has no optimize for all device. also this method doesn't show Internet available" +
            "on your device.")
fun ATHViewModel.checkInternetConnection(): Boolean {
    val connectivityManager = getApplication<ATHGlobal>().getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.activeNetworkInfo?.run {
            return when (type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        }
    }
    return false
}