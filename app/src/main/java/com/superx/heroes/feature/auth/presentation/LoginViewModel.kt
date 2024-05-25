package com.superx.heroes.feature.auth.presentation

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.superx.heroes.database.SuperXPrefs
import com.superx.heroes.feature.auth.domain.use_case.GoogleAuthUseCases
import com.superx.heroes.util.Constants.RC_SIGN_IN
import com.superx.heroes.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val googleAuthUseCases: GoogleAuthUseCases,
    private val onActivityResultFlow: MutableSharedFlow<Pair<Int, Intent?>>,
    private val prefs: SuperXPrefs
    ) : ViewModel() {

    private val _signInResult: MutableStateFlow<Response<FirebaseUser>> =
        MutableStateFlow(Response.Idle)
    val signInResult: StateFlow<Response<FirebaseUser>> = _signInResult.asStateFlow()

    init {
        viewModelScope.launch {
            onActivityResultFlow
                .collect { pair ->
                    if (pair.first == RC_SIGN_IN) {
                        googleAuthUseCases.signIn(pair.second)
                            .onStart {
                                _signInResult.emit(Response.Loading)
                            }
                            .catch {
                                _signInResult.emit(Response.Error(it))
                            }
                            .collect { firebaseUser ->
                                firebaseUser?.let {
                                    it.apply {
                                        prefs.userId = uid
                                        prefs.userDisplayName = displayName.toString()
                                        prefs.userPhotoUrl = photoUrl.toString()
                                    }
                                    _signInResult.emit(Response.Success(it))
                                } ?: run {
                                    _signInResult.emit(Response.Error(Throwable(message = "User not found")))
                                }
                            }
                    }
                }
        }
    }

    fun signInWithGoogle(activity: Activity) = viewModelScope.launch {
        val sigInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(sigInIntent, RC_SIGN_IN)
    }

    fun isLoggedIn(invoke: (FirebaseUser) -> Unit) {
        auth.currentUser?.let {
            invoke(it)
        }
    }
}