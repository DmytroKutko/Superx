package com.superx.heroes.feature.user.presentation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.superx.heroes.feature.core.presentation.FullScreenCircleImage
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily
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
    val context = LocalContext.current
    val currentUser = viewModel.currentUser.collectAsStateWithLifecycle()

    val expanded = mutableStateOf(false)
    val userPhoto = currentUser.value.userPhotoUrl
        ?: "https://media.tenor.com/duqWIHQs5BkAAAAe/%D0%B9%D0%BE%D0%B9-%D0%BD%D0%B0%D0%B9-%D0%B1%D1%83%D0%B4%D0%B5.png"

    val painter: Painter = rememberAsyncImagePainter(userPhoto)

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
                    ProfileDropdownMenu(expanded = expanded, onItemClicked = { menuItem ->
                        when (menuItem) {
                            ProfilePopupMenu.EDIT -> {
                                Toast.makeText(context.applicationContext, "In progress...", Toast.LENGTH_SHORT).show()
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
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .height(240.dp)
                        .width(180.dp)
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        painter = painter,
                        contentDescription = "Image loaded from URL"
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = currentUser.value.userName.ifEmpty { "Unknown"},
                    fontFamily = ubuntuFontFamily,
                    fontSize = 20.sp
                )
            }
        }
    }
}