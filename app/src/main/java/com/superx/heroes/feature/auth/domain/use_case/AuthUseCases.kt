package com.superx.heroes.feature.auth.domain.use_case

data class AuthUseCases(
    val googleSignIn: GoogleSignInUseCase,
    val signOut: SignOutUseCase,
    val facebookSignIn: FacebookSignInUseCase,
)