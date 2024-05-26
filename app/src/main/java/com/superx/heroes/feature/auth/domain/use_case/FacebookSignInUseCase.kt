package com.superx.heroes.feature.auth.domain.use_case

import android.os.Bundle
import android.util.Log
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.superx.heroes.database.SuperXPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONException
import javax.inject.Inject
import kotlin.coroutines.resume

class FacebookSignInUseCase @Inject constructor(
    private val prefs: SuperXPrefs,
) {
    suspend operator fun invoke(): Flow<Boolean> = flow {
        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null) {
            val success = suspendCancellableCoroutine { continuation ->
                GraphRequest.newMeRequest(accessToken) { json, response ->
                    try {
                        if (json != null) {
                            val userId = json.getString("id")
                            val userName = json.getString("name")
                            val userProfilePic = json.getJSONObject("picture").getJSONObject("data").getString("url")

                            prefs.userId = userId
                            prefs.userDisplayName = userName
                            prefs.userPhotoUrl = userProfilePic

                            continuation.resume(true)
                        } else {
                            continuation.resume(false)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        continuation.resume(false)
                    }
                }.apply {
                    parameters = Bundle().apply {
                        putString("fields", "id,name,picture.type(large)")
                    }
                }.executeAsync()
            }
            emit(success)
        } else {
            emit(false)
        }
    }
}