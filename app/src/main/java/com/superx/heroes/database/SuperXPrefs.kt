package com.superx.heroes.database

import android.content.SharedPreferences
import com.superx.heroes.util.Constants.PHOTO_URL
import com.superx.heroes.util.Constants.USER_ID
import com.superx.heroes.util.Constants.USER_NAME
import javax.inject.Inject

interface SuperXPrefs {
    var userId: String
    var userDisplayName: String
    var userPhotoUrl: String?
}

class SuperXPrefsImpl @Inject constructor(
    private val prefs: SharedPreferences,
) : SuperXPrefs {

    override var userPhotoUrl: String?
        set(value) = prefs.edit().putString(PHOTO_URL, value).apply()
        get() = prefs.getString(PHOTO_URL, null)

    override var userId: String
        set(value) = prefs.edit().putString(USER_ID, value).apply()
        get() = prefs.getString(USER_ID, "") ?: ""

    override var userDisplayName: String
        set(value) = prefs.edit().putString(USER_NAME, value).apply()
        get() = prefs.getString(USER_NAME, "") ?: ""
}