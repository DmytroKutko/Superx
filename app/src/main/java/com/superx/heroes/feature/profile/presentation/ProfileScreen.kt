package com.superx.heroes.feature.profile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.superx.heroes.database.model.Powerstats
import com.superx.heroes.feature.core.ui.theme.ubuntuFontFamily
import com.superx.heroes.feature.profile.presentation.components.PowerstatsComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    heroId: Int,
    viewModel: ProfileViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    viewModel.getHeroById(heroId)
    val hero by viewModel.hero.collectAsStateWithLifecycle()

    val samplePowerstats = Powerstats(
        intelligence = hero.powerstats.intelligence,
        strength = hero.powerstats.strength,
        speed = hero.powerstats.speed,
        durability = hero.powerstats.durability,
        power = hero.powerstats.power,
        combat = hero.powerstats.combat
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = hero.name, fontFamily = ubuntuFontFamily) },
                actions = {
                    IconButton(onClick = {
                        if (hero.isBookmarked) {
                            val updatedHero = hero.copy(isBookmarked = false)
                            viewModel.updateHero(updatedHero)
                        } else {
                            val updatedHero = hero.copy(isBookmarked = true)
                            viewModel.updateHero(updatedHero)
                        }
                    }) {
                        if (hero.isBookmarked) {
                            Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                        } else {
                            Icon(imageVector = Icons.Outlined.StarOutline, contentDescription = null)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Power Stats",
                fontFamily = ubuntuFontFamily,
            )
            PowerstatsComponent(samplePowerstats)
        }
    }

}