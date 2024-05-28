package com.superx.heroes.feature.auth.presentation

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.superx.heroes.feature.auth.domain.use_case.AuthUseCases
import com.superx.heroes.util.Constants.FACEBOOK_SIGN_IN
import com.superx.heroes.util.Constants.GOOGLE_SIGN_IN
import com.superx.heroes.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val authUseCases: AuthUseCases,
    private val onActivityResultFlow: MutableSharedFlow<Pair<Int, Intent?>>,
    private val loginManager: LoginManager,
) : ViewModel() {

    private val _signInResult: MutableStateFlow<Response<Boolean>> = MutableStateFlow(Response.Idle)
    val signInResult: StateFlow<Response<Boolean>> = _signInResult.asStateFlow()

    init {
        viewModelScope.launch {
            isLoggedIn()

            onActivityResultFlow
                .collect { pair ->
                    if (pair.first == GOOGLE_SIGN_IN) {
                        authUseCases.googleSignIn(pair.second)
                            .catch {
                                _signInResult.emit(Response.Error(it))
                            }
                            .collect { firebaseUser ->
                                firebaseUser.let { success ->
                                    if (success)
                                        _signInResult.emit(Response.Success(true))
                                    else
                                        _signInResult.emit(Response.Error(Throwable("Error sign in")))
                                }
                            }
                    } else if (pair.first == FACEBOOK_SIGN_IN) {
                        authUseCases.facebookSignIn()
                            .catch {
                                _signInResult.emit(Response.Error(it))
                            }
                            .collect { success ->
                                if (success)
                                    _signInResult.emit(Response.Success(success))
                                else
                                    _signInResult.emit(Response.Error(Throwable("Error sign in")))
                            }
                    }
                }
        }
    }

    private fun isLoggedIn() = viewModelScope.launch {
        _signInResult.emit(Response.Loading)
        if (isUserLoggedIn()) {
            _signInResult.emit(Response.Success(true))
        } else {
            _signInResult.emit(Response.Idle)
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedInFacebook = accessToken != null && !accessToken.isExpired
        val isLoggedInFirebase = auth.currentUser != null

        return isLoggedInFacebook || isLoggedInFirebase
    }

    fun signInWithGoogle(activity: Activity) = viewModelScope.launch {
        val sigInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(sigInIntent, GOOGLE_SIGN_IN)
    }

    fun signInWithFacebook(context: Activity) = viewModelScope.launch {
        loginManager.logInWithReadPermissions(context, listOf("email"))
    }

    fun signInWithEmailAndPassword(context: Activity, email: String, password: String) =
        viewModelScope.launch {
            _signInResult.emit(Response.Loading)

            auth.signInWithEmailAndPassword(email, password)
                .addOnCanceledListener {
                    viewModelScope.launch {
                        _signInResult.emit(Response.Idle)
                    }
                }
                .addOnFailureListener {
                    viewModelScope.launch {
                        _signInResult.emit(Response.Error(Throwable(it.message.toString())))
                    }
                }
                .addOnCompleteListener(context) { task ->
                    if (task.isSuccessful) {
                        viewModelScope.launch {
                            _signInResult.emit(Response.Success(true))
                        }
                    } else {
                        viewModelScope.launch {
                            _signInResult.emit(Response.Error(Throwable(task.exception?.message.toString())))
                        }
                    }
                }
        }

    fun signUpWithEmailAndPassword(context: Activity, email: String, password: String) =
        viewModelScope.launch {
            _signInResult.emit(Response.Loading)

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCanceledListener {
                    viewModelScope.launch {
                        _signInResult.emit(Response.Idle)
                    }
                }
                .addOnFailureListener {
                    viewModelScope.launch {
                        _signInResult.emit(Response.Error(Throwable(it.message.toString())))
                    }
                }
                .addOnCompleteListener(context) { task ->
                    if (task.isSuccessful) {
                        viewModelScope.launch {
                            _signInResult.emit(Response.Success(true))
                        }
                    } else {
                        viewModelScope.launch {
                            _signInResult.emit(Response.Error(Throwable(task.exception?.message.toString())))
                        }
                    }
                }
        }
}