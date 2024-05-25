package com.superx.heroes.feature.user.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.superx.heroes.feature.core.presentation.FullScreenCircleImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onSignOutClicked: () -> Unit,
    viewModel: UserProfileViewModel = hiltViewModel(),
) {

    val currentUser = viewModel.currentUser.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Your profile")})
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                currentUser.value.userPhotoUrl?.let {
                    FullScreenCircleImage(it)
                } ?: run {
                    FullScreenCircleImage("https://media.tenor.com/duqWIHQs5BkAAAAe/%D0%B9%D0%BE%D0%B9-%D0%BD%D0%B0%D0%B9-%D0%B1%D1%83%D0%B4%D0%B5.png")
                }

                Spacer(modifier = Modifier.size(16.dp))

                Text(text = currentUser.value.userName)

                Spacer(modifier = Modifier.size(16.dp))

                Button(onClick = {
                    viewModel.signOut()
                    onSignOutClicked()
                }) {
                    Text(text = "Sign out")
                }
            }
        }
    }
}