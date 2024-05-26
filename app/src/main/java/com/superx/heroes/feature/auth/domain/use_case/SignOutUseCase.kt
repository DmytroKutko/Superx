package com.superx.heroes.feature.auth.domain.use_case

import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.superx.heroes.database.SuperXPrefs
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val prefs: SuperXPrefs
) {
    operator fun invoke() {
        auth.signOut()
        googleSignInClient.signOut()
        LoginManager.getInstance().logOut()
        prefs.userId = ""
        prefs.userDisplayName = ""
        prefs.userPhotoUrl = null
    }
}