package com.superx.heroes.feature.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily

@Composable
fun ErrorScreen(
    msg: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Rounded.ErrorOutline,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                text = msg,
                fontFamily = ubuntuFontFamily,
                fontSize = 16.sp,
            )
        }
    }
}