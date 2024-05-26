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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.superx.heroes.feature.auth.presentation.components.SignUpForm
import com.superx.heroes.feature.core.presentation.LoadingScreen
import com.superx.heroes.util.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSignUp: () -> Unit,
    onLoginClicked: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val response by viewModel.signInResult.collectAsStateWithLifecycle()

    val context = LocalContext.current as Activity

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .shadow(elevation = 8.dp),
                title = { Text(text = "Sign up") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            when (response) {
                is Response.Error, Response.Idle -> {

                    SignUpForm(
                        onSignUpClicked = { email, password ->
                            viewModel.signUpWithEmailAndPassword(context, email, password)
                        },
                        onLoginClicked = onLoginClicked
                    )
                }

                Response.Loading -> {
                    LoadingScreen()
                }

                is Response.Success -> onSignUp()
            }
        }
    }
}