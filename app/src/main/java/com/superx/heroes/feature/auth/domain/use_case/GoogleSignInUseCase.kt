package com.superx.heroes.feature.auth.domain.use_case

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.superx.heroes.database.SuperXPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

data class GoogleSignInUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val prefs: SuperXPrefs
) {
    suspend operator fun invoke(data: Intent?): Flow<Boolean> = flow {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val authResult = auth.signInWithCredential(credential).await()

            authResult.user?.apply {
                prefs.userId = uid
                prefs.userDisplayName = displayName.toString()
                prefs.userPhotoUrl = photoUrl.toString()
                emit(true)
            } ?: run {
                emit(false)
            }
        } catch (e: Exception) {
            emit(false)
        }
    }
}