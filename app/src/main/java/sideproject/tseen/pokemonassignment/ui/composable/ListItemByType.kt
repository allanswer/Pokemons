package sideproject.tseen.pokemonassignment.ui.theme.listItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import sideproject.tseen.pokemonassignment.model.PokemonInfo
import sideproject.tseen.pokemonassignment.viewmodel.PokemonViewModel

@Composable
fun ListItemByType(navController: NavController, pokemonInfo: PokemonInfo, pokemonViewModel: PokemonViewModel) {
    Box(
        modifier = Modifier.size(128.dp)
        .clickable {
            navController.navigate("pokemonDetails/${pokemonInfo.pokemonName}")
        },
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(modifier = Modifier.weight(0.9f),
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
                modifier = Modifier.weight(0.1f)
            )
        }
    }
}
