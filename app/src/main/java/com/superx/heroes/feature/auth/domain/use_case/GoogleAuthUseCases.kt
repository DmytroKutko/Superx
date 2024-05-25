package com.superx.heroes.feature.auth.domain.use_case

data class GoogleAuthUseCases(
    val signIn: GoogleSignInUseCase,
    val signOut: GoogleSignOutUseCase,
)