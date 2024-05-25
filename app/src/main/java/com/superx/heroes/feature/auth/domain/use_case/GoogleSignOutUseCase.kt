package com.superx.heroes.feature.auth.domain.use_case

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import javax.inject.Inject

class GoogleSignOutUseCase @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
) {
    operator fun invoke() {
       googleSignInClient.signOut()
    }
}