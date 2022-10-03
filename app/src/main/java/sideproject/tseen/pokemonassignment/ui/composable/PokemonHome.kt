package sideproject.tseen.pokemonassignment.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import sideproject.tseen.pokemonassignment.ui.theme.listItem.ListItemByType
import sideproject.tseen.pokemonassignment.viewmodel.PokemonViewModel
private val TAG = "PokemonHome"

@Composable
fun PokemonHome(navController: NavController, pokemonViewModel: PokemonViewModel) {

    val allTypes = pokemonViewModel.getAllTypes().observeAsState(initial = emptyList())


    LazyColumn() {
        items(allTypes.value) { eachType ->
            val pokemons = pokemonViewModel.getPokemonsOfType(eachType.typeName).observeAsState(initial = emptyList())
            if(pokemons.value.isNotEmpty() && pokemons.value[0].pokemons.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {

                    Text(
                        eachType.typeName.capitalize(),
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Text(pokemons.value[0].pokemons.size.toString(),
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp)
                }
                LazyRow(contentPadding = PaddingValues(all = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(pokemons.value[0].pokemons.sortedBy { it.id }) {
                        ListItemByType(navController = navController, pokemonInfo = it, pokemonViewModel)
                    }
                }
            }
        }
    }
}



