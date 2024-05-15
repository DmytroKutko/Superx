package com.superx.heroes.feature.profile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.superx.heroes.R
import com.superx.heroes.database.model.Powerstats
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily

@Composable
fun PowerstatsComponent(
    powerstats: Powerstats,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StatWithIndicator("Intelligence", R.drawable.brainstorm, powerstats.intelligence)
        StatWithIndicator("Strength", R.drawable.strength, powerstats.strength)
        StatWithIndicator("Speed", R.drawable.running, powerstats.speed)
        StatWithIndicator("Durability", R.drawable.durability, powerstats.durability)
        StatWithIndicator("Power", R.drawable.power, powerstats.power)
        StatWithIndicator("Combat", R.drawable.combat, powerstats.combat)
    }
}

@Composable
fun StatWithIndicator(
    statName: String,
    image: Int,
    value: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier
                .size(32.dp)
                .padding(4.dp),
            painter = painterResource(id = image),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .width(120.dp),
            text = statName,
            fontFamily = ubuntuFontFamily
        )
        StatIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = value
        )
    }
}

@Composable
fun StatIndicator(
    value: Int,
    modifier: Modifier,
) {
    val percentage = (value / 100f) * 100

    Card(
        modifier = Modifier
            .size(width = 200.dp, height = 40.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxHeight()
                    .widthIn(max = 200.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(25.dp)
                    )
            ) {
                Spacer(modifier = Modifier.width(percentage * 2.dp))
            }
            Text(
                text = value.toString(),
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontFamily = ubuntuFontFamily
            )
        }
    }
}

@Preview
@Composable
private fun PowerstatsRowPreview() {
    val samplePowerstats = Powerstats(
        intelligence = 80,
        strength = 70,
        speed = 90,
        durability = 60,
        power = 49,
        combat = 5
    )

    PowerstatsComponent(
        powerstats = samplePowerstats,
    )
}