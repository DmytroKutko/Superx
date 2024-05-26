package com.superx.heroes.feature.profile.presentation.components

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily
import com.superx.heroes.feature.profile.data.BirthLocation
import com.superx.heroes.util.isAddressValid

@SuppressLint("UnrememberedMutableState")
@Composable
fun AnnotatedText(
    title: String,
    descritpion: String,
) {
    val context = LocalContext.current
    val location: MutableState<BirthLocation?> = remember { mutableStateOf(null) }

    isAddressValid(context, descritpion) {
        it?.let { birthLocation ->
            location.value = birthLocation
        }
    }

    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    fontFamily = ubuntuFontFamily,
                    color = Color(0XFF3559E0),
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append("$title ")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    fontFamily = ubuntuFontFamily,
                ),
            ) {
                if (location.value != null) {
                    pushStringAnnotation(
                        tag = "URL",
                        annotation = "geo:${location.value?.latitude},${location.value?.longitude}"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append("$descritpion")
                    }
                    pop()
                } else {
                    append("$descritpion")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 14.dp)
            .then(
                if (location.value != null) {
                    Modifier.clickable(onClick = {
                        val uri = Uri.parse("geo:${location.value?.latitude},${location.value?.longitude}")
                        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                            setPackage("com.google.android.apps.maps")
                        }
                        context.startActivity(intent)
                    })
                } else {
                    Modifier
                }
            )
    )
}