package sideproject.tseen.pokemonassignment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import sideproject.tseen.pokemonassignment.model.PokemonCardDetail
import sideproject.tseen.pokemonassignment.model.PokemonInfo
import sideproject.tseen.pokemonassignment.ui.composable.CircularProgressBar
import sideproject.tseen.pokemonassignment.viewmodel.PokemonViewModel

@Composable
fun PokemonDetails(navController: NavController, pokemonNAme: String, pokemonViewModel:PokemonViewModel) {
    val pokemonCardDetails = pokemonViewModel.getPokemonSpecieCardDetail(pokemonNAme).observeAsState(initial = emptyList())
    val pokemonInfos = pokemonViewModel.getPokemonInfo(pokemonNAme).observeAsState(initial = emptyList())

    if(pokemonCardDetails.value.isNotEmpty() && pokemonInfos.value.isNotEmpty()) {
        val pokemonCardDetail = remember {
            pokemonCardDetails.value[0]
        }
        val pokemonInfo = remember {
            pokemonInfos.value[0]
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {
                    navController.navigateUp()},
                    colors = ButtonDefaults.buttonColors(Color.Transparent,
                        contentColor = Color.Black),

                    ) {
                    Icon(Icons.Rounded.ArrowBack, "")
                }
                Text("#" + pokemonInfo.id.toString(),
                    fontSize = 20.sp)
            }


            Box(modifier = Modifier
                .fillMaxWidth(),
                contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = pokemonInfo.imageUrl,
                    contentDescription = null,
                )
            }

            Text(pokemonInfo.pokemonName.capitalize())

            //Types
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                pokemonInfo.pokemonType.map {
                    Column(
                        modifier = Modifier
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .padding(8.dp, 4.dp)) {
                        Text(text = it)
                    }
                }
            }

            //Pass the evolveFrom Pokemon name to get the corresponding information.
            val fromPokemonInfos = pokemonViewModel.getPokemonInfo(pokemonCardDetail.evolveFrom.toString()).observeAsState(initial = emptyList())
            if(pokemonCardDetail.evolveFrom != null && fromPokemonInfos.value.isNotEmpty()) {
                //Composable section for evolution chain
                pokemonEvolveFromSection(navController, fromPokemonInfos.value[0], pokemonCardDetail)
            }

            Text(pokemonCardDetail.desc.toString(), textAlign = TextAlign.Start)
        }
    }
}


@Composable
fun pokemonEvolveFromSection(navController: NavController, fromPokemonInfo: PokemonInfo, pokemonCardDetail: PokemonCardDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("pokemonDetails/${pokemonCardDetail.evolveFrom}")
            },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically) {

        Column() {
            Text("Evolves from", fontSize = 8.sp, color = Color.Gray)
            Spacer(modifier = Modifier.size(2.dp))
            Text(pokemonCardDetail.evolveFrom!!.capitalize(), fontSize = 12.sp)
        }
        Box(modifier = Modifier.size(48.dp)) {
            AsyncImage(
                model = fromPokemonInfo.imageUrl,
                contentDescription = null,
            )
        }

    }
}

@Composable
fun PokemonDetailPre() {
    val navController = rememberNavController()
    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                navController.navigateUp()},
                colors = ButtonDefaults.buttonColors(Color.Transparent,
                contentColor = Color.Black),

            ) {
                Icon(Icons.Rounded.ArrowBack, "")
            }
            Text("#3",
            fontSize = 24.sp)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(256.dp),
            contentAlignment = Alignment.Center) {
            AsyncImage(
                model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/1.svg",
                contentDescription = null,
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text("Water",
            textAlign = TextAlign.Center)
            Text("Dragon",
                textAlign = TextAlign.Center)
        }

        Text("Its short feet are tipped with suction\\npads that enable it to tirelessly climb\\nslopes and walls.")
    }
}

@Preview
@Composable
fun ComposablePreview() {
    PokemonDetailPre()
}

