package com.superx.heroes.feature.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.superx.heroes.database.SuperXPrefs
import com.superx.heroes.feature.auth.domain.use_case.GoogleAuthUseCases
import com.superx.heroes.feature.user.data.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val prefs: SuperXPrefs,
    private val googleAuthUseCases: GoogleAuthUseCases,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _currentUser: MutableStateFlow<UserProfile> = MutableStateFlow(UserProfile("", "", null))
    val currentUser : StateFlow<UserProfile> = _currentUser.asStateFlow()

    init {
        viewModelScope.launch {
            val data = _currentUser.value.copy(
                userId = prefs.userId,
                userName = prefs.userDisplayName,
                userPhotoUrl = prefs.userPhotoUrl
            )
            _currentUser.emit(data)
        }
    }

    fun signOut() = viewModelScope.launch {
        auth.signOut()
        googleAuthUseCases.signOut()
    }
}