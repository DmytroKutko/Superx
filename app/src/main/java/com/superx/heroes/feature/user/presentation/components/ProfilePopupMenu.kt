package com.superx.heroes.feature.user.presentation.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun ProfileDropdownMenu(
    expanded: MutableState<Boolean>,
    onItemClicked: (ProfilePopupMenu) -> Unit,
) {
    DropdownMenu(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        DropdownMenuItem(
            leadingIcon = {
            Icon(
                imageVector = Icons.Filled.EditNote,
                contentDescription = null
            )
        }, text = { Text(text = "Edit profile") }, onClick = {
            onItemClicked(ProfilePopupMenu.EDIT)
        })

        DropdownMenuItem(
            leadingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = null
            )
        }, text = { Text(text = "Sign out") }, onClick = {
            onItemClicked(ProfilePopupMenu.SIGN_OUT)
        })
    }
}

enum class ProfilePopupMenu {
    EDIT, SIGN_OUT
}