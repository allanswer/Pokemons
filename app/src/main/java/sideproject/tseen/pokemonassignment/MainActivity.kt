package sideproject.tseen.pokemonassignment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import sideproject.tseen.pokemonassignment.api.PokemonService
import sideproject.tseen.pokemonassignment.model.PokemonInfo
import sideproject.tseen.pokemonassignment.model.response.PokemonList
import sideproject.tseen.pokemonassignment.model.response.PokemonTypeResponse
import sideproject.tseen.pokemonassignment.model.type.type
import sideproject.tseen.pokemonassignment.persistence.PokemonDatabase
import sideproject.tseen.pokemonassignment.ui.PokemonDetails
import sideproject.tseen.pokemonassignment.ui.PokemonHome
import sideproject.tseen.pokemonassignment.ui.theme.PokemonAssignmentTheme
import sideproject.tseen.pokemonassignment.viewmodel.PokemonViewModel
import kotlin.math.log

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private lateinit var mPokemonViewModel: PokemonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pokemonService = PokemonService.getInstance()
        mPokemonViewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            mPokemonViewModel.getPokemonRes(pokemonService) //Build Pokemon PokemonInfo and PokemonTypeCrossRef Table
            mPokemonViewModel.getAllPokemonTypes(pokemonService) // Build Type Table

        }
        setContent {
            PokemonAssignmentTheme() {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "Catalog"
                ) {
                    composable("Catalog") {
                        PokemonHome(navController, pokemonViewModel = mPokemonViewModel)
                    }

                    composable(route = "pokemonDetails/{pokemonName}",
                        arguments = listOf(
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        )
                    ){
                        val pokemonNAme = it.arguments?.getString("pokemonName")!!
                        PokemonDetails(navController = navController, pokemonNAme, mPokemonViewModel)
                    }
                }

            }
        }
    }
}





