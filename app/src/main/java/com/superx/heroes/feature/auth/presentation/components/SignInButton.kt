package com.superx.heroes.feature.auth.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.superx.heroes.R

@Composable
fun SignInButton(
    resource: Int,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .border(BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondaryContainer), CircleShape),
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp),
            painter = painterResource(id = resource),
            contentDescription = "Google Sign-In",
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
private fun GoogleSignInButtonPreview() {
    SignInButton(
        resource = R.drawable.ic_google_logo,
        onClick = {}
    )
}

