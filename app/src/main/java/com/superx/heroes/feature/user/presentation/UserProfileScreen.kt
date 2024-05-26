package com.superx.heroes.feature.user.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.superx.heroes.feature.core.presentation.FullScreenCircleImage
import com.superx.heroes.feature.user.presentation.components.ProfileDropdownMenu
import com.superx.heroes.feature.user.presentation.components.ProfilePopupMenu

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onEditClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    viewModel: UserProfileViewModel = hiltViewModel(),
) {
    val currentUser = viewModel.currentUser.collectAsStateWithLifecycle()

    val expanded = mutableStateOf(false)

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .shadow(elevation = 8.dp),
            title = { Text(text = "Your profile") },
            actions = {
                IconButton(onClick = {
                    expanded.value = true
                }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                    ProfileDropdownMenu(expanded = expanded, onItemClicked = {
                        when (it) {
                            ProfilePopupMenu.EDIT -> {
                                onEditClicked()
                            }
                            ProfilePopupMenu.SIGN_OUT -> {
                                viewModel.signOut()
                                onSignOutClicked()
                            }
                        }
                    })
                }
            })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
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
            }
        }
    }
}