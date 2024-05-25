package com.superx.heroes.feature.auth.presentation

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Box
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseUser
import com.superx.heroes.feature.core.presentation.LoadingScreen
import com.superx.heroes.util.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSuccessLogin: (FirebaseUser) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current as Activity

    val response by viewModel.signInResult.collectAsStateWithLifecycle()

    viewModel.isLoggedIn {
        onSuccessLogin(it)
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
                    Button(onClick = {
                        viewModel.signInWithGoogle(context)
                    }) {
                        Text(text = "Continue with Google")
                    }
                    Toast.makeText(context.applicationContext, "Error loading", Toast.LENGTH_SHORT).show()
                }
                Response.Idle -> {
                    Button(onClick = {
                        viewModel.signInWithGoogle(context)
                    }) {
                        Text(text = "Continue with Google")
                    }
                }
                Response.Loading -> {
                    LoadingScreen()
                }
                is Response.Success -> onSuccessLogin((response as Response.Success<FirebaseUser>).data)
            }
        }
    }
}