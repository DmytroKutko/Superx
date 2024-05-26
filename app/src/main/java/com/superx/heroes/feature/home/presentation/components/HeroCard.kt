package com.superx.heroes.feature.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.superx.heroes.database.model.Hero
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily

@Composable
fun HeroCard(
    hero: Hero,
    onHeroClick: (Int) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 6.dp, end = 6.dp)
            .clickable {
                onHeroClick(hero.id)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = hero.avatarUrl

        val painter: Painter = rememberAsyncImagePainter(imageUrl)

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.TopEnd
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(180.dp),
                    painter = painter,
                    contentDescription = "Image loaded from URL"
                )
            }

            if (hero.isBookmarked) {
                Icon(
                    modifier = Modifier
                        .size(34.dp)
                        .padding(4.dp),
                    imageVector = Icons.Filled.Star,
                    tint = Color.Yellow,
                    contentDescription = null,
                )
            }
        }

        Text(
            text = hero.name,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontFamily = ubuntuFontFamily,
        )
    }
}