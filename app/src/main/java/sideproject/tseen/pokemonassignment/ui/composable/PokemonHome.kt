package sideproject.tseen.pokemonassignment.ui.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import sideproject.tseen.pokemonassignment.ui.theme.listItem.ListItemByType
import sideproject.tseen.pokemonassignment.util.Constants
import sideproject.tseen.pokemonassignment.viewmodel.PokemonViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonHome(navController: NavController, pokemonViewModel: PokemonViewModel) {
    val allTypes = pokemonViewModel.getAllTypes().observeAsState(initial = emptyList())
    val allPokemon = pokemonViewModel.getAllPokemon().observeAsState(initial = emptyList())
    val loading = pokemonViewModel.loadingHome.value
    val progress = pokemonViewModel.progress.value
    Column {
        CircularProgressBar(loading, progress)
        LazyColumn{
            stickyHeader(){
                Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)) {
                    LazyRowTopBar(eachType = "Demo", allPokemon.value.size)
                    LazyRow(contentPadding = PaddingValues(all = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(allPokemon.value.sortedBy {it.name }) {
                            val pokemonInfo = pokemonViewModel.getPokemonInfo(it.name).observeAsState(initial = emptyList())
                            if(pokemonInfo.value.isNotEmpty()) {
                                ListItemByType(navController = navController, pokemonInfo = pokemonInfo.value[0], Constants.STICKY_LIST_ITEM_BOX_SIZE)
                            }
                        }
                    }
                }
            }
            items(allTypes.value) { eachType ->
                val pokemonState = pokemonViewModel.getPokemonsOfType(eachType.typeName).observeAsState(initial = emptyList())
                if(pokemonState.value.isNotEmpty() && pokemonState.value[0].pokemons.isNotEmpty()) {
                    LazyRowTopBar(eachType = eachType.typeName, pokemonCount = pokemonState.value[0].pokemons.size)
                    LazyRow(contentPadding = PaddingValues(all = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(pokemonState.value[0].pokemons.sortedBy { it.id }) {
                            ListItemByType(navController = navController, pokemonInfo = it, Constants.LIST_ITEM_BOX_SIZE)
                        }
                    }
                }
            }
        }
    }

}
@Composable
fun LazyRowTopBar(eachType: String, pokemonCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {

        Text(
            eachType.capitalize(),
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Text(pokemonCount.toString(),
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp)
    }

}



