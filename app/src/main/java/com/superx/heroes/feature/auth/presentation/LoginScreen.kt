package com.superx.heroes.feature.auth.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.superx.heroes.feature.auth.presentation.components.LoginComponent
import com.superx.heroes.feature.core.presentation.LoadingScreen
import com.superx.heroes.util.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSuccessLogin: () -> Unit,
    onSignUpClicked: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val context = LocalContext.current as Activity

    val response by viewModel.signInResult.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .shadow(elevation = 8.dp),
                title = { Text(text = "Login") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            when (response) {
                is Response.Error, Response.Idle -> {
                    LoginComponent(
                        signInWithEmailAndPassword = { email, password ->
                            viewModel.signInWithEmailAndPassword(context, email, password)
                        },
                        signInWithGoogle = {
                            viewModel.signInWithGoogle(context)
                        },
                        signInWithFacebook = {
                            viewModel.signInWithFacebook(context)
                        },
                        signUp = onSignUpClicked
                    )
                }

                Response.Loading -> {
                    LoadingScreen()
                }

                is Response.Success -> onSuccessLogin()
            }
        }
    }
}