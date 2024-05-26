package com.superx.heroes.feature.auth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superx.heroes.R
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily

@Composable
fun SignInButton(
    resource: Int,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .width(220.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = resource),
                contentDescription = "Google Sign-In",
                tint = Color.Unspecified
            )
            Text(
                text = text,
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 8.dp),
                fontFamily = ubuntuFontFamily
            )
        }
    }
}

@Preview
@Composable
private fun GoogleSignInButtonPreview() {
    SignInButton(
        resource = R.drawable.ic_google_logo,
        text = stringResource(R.string.sign_in_with_google),
        onClick = {}
    )
}

