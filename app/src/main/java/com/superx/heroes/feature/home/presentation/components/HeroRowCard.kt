package com.superx.heroes.feature.home.presentation.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.superx.heroes.database.model.Hero
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily

@Composable
fun HeroRowCard(
    hero: Hero,
    onHeroClick: (Int) -> Unit,
) {

    val painter: Painter = rememberAsyncImagePainter(hero.avatarUrl)

    Column(
        modifier = Modifier
            .clickable {
                onHeroClick(hero.id)
            }
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .height(160.dp)
                .width(100.dp),
            shape = RoundedCornerShape(18.dp)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                painter = painter,
                contentDescription = "Image loaded from URL"
            )
        }

        Spacer(
            modifier = Modifier
                .padding(4.dp)
        )

        Text(
            text = hero.name,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontFamily = ubuntuFontFamily,
        )
    }
}