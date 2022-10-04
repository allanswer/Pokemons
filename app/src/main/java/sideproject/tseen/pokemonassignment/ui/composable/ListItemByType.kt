package sideproject.tseen.pokemonassignment.ui.theme.listItem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import sideproject.tseen.pokemonassignment.model.PokemonInfo

@Composable
fun ListItemByType(navController: NavController, pokemonInfo: PokemonInfo, boxSize: Int) {
    Box(
        modifier = Modifier.size(boxSize.dp)
        .clickable {
            navController.navigate("pokemonDetails/${pokemonInfo.pokemonName}")
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Box(modifier = Modifier.weight(0.8f),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = pokemonInfo.imageUrl,
                    contentDescription = null,
                )
            }
            Text(pokemonInfo.pokemonName.capitalize(),
                fontSize = 8.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(0.2f)
            )
        }
    }
}


