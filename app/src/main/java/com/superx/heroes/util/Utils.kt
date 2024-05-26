package com.superx.heroes.util

import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.util.Patterns
import com.superx.heroes.feature.profile.data.BirthLocation
import java.io.IOException
import java.util.Locale

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    return password.length >= 6
}

fun isAddressValid(context: Context, address: String, callback: (BirthLocation?) -> Unit) {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addressList = geocoder.getFromLocationName(address, 1)
        if (!addressList.isNullOrEmpty()) {
            val location = addressList[0]
            Log.d("Geocode", "Latitude: ${location.latitude}, Longitude: ${location.longitude}")
            callback(BirthLocation(latitude = location.latitude, longitude = location.longitude))
        } else {
            callback(null) // Address is invalid
        }
    } catch (e: IOException) {
        e.printStackTrace()
        callback(null) // Address is invalid
    }
}