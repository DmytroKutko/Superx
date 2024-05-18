package com.superx.heroes.feature.profile.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily

@Composable
fun AnnotatedText(
    title: String,
    descritpion: String,
    modifier: Modifier = Modifier,
) {
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
                append(title)
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    fontFamily = ubuntuFontFamily,
                )
            ) {
                append(" $descritpion")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 14.dp),
    )
}