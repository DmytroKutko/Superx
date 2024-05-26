package com.superx.heroes.feature.auth.presentation

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseUser
import com.superx.heroes.R
import com.superx.heroes.feature.auth.presentation.components.SignInButton
import com.superx.heroes.feature.core.presentation.LoadingScreen
import com.superx.heroes.util.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSuccessLogin: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current as Activity

    val response by viewModel.signInResult.collectAsStateWithLifecycle()

    viewModel.isLoggedIn {
        onSuccessLogin()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Login") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            when (response) {
                is Response.Error -> {
                    Column {
                        SignInButton(
                            resource = R.drawable.ic_google_logo,
                            text = stringResource(R.string.sign_in_with_google),
                        ) {
                            viewModel.signInWithGoogle(context)
                        }

                        SignInButton(
                            resource = R.drawable.ic_facebook_logo,
                            text = stringResource(R.string.sign_in_with_facebook),
                        ) {
                            viewModel.signInWithFacebook(context)
                        }
                    }
                    Toast.makeText(context.applicationContext, "Error loading", Toast.LENGTH_SHORT).show()
                }
                Response.Idle -> {
                    Column {
                        SignInButton(
                            resource = R.drawable.ic_google_logo,
                            text = stringResource(R.string.sign_in_with_google),
                        ) {
                            viewModel.signInWithGoogle(context)
                        }

                        SignInButton(
                            resource = R.drawable.ic_facebook_logo,
                            text = stringResource(R.string.sign_in_with_facebook),
                        ) {
                            viewModel.signInWithFacebook(context)
                        }
                    }
                }
                Response.Loading -> {
                    LoadingScreen()
                }
                is Response.Success -> onSuccessLogin()
            }
        }
    }
}