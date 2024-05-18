package com.superx.heroes.feature.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter

@Composable
fun FullScreenCircleImage(
    imageResource: String,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(imageResource),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(200.dp)
                .clickable {
                    isExpanded = true
                },
            contentScale = ContentScale.Crop
        )

        if (isExpanded) {
            FullScreenImageDialog(
                imageResource = imageResource,
                onClose = { isExpanded = false }
            )
        }
    }
}

@Composable
fun FullScreenImageDialog(
    imageResource: String,
    onClose: () -> Unit
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .clickable { onClose() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageResource),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}