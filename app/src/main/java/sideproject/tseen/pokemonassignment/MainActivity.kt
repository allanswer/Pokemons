package sideproject.tseen.pokemonassignment

import android.graphics.Bitmap
import android.os.Bundle
import android.util.LruCache
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import sideproject.tseen.pokemonassignment.api.PokemonService
import sideproject.tseen.pokemonassignment.ui.PokemonDetails
import sideproject.tseen.pokemonassignment.ui.composable.PokemonHome
import sideproject.tseen.pokemonassignment.ui.theme.PokemonAssignmentTheme
import sideproject.tseen.pokemonassignment.viewmodel.PokemonViewModel

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private lateinit var mPokemonViewModel: PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pokemonService = PokemonService.getInstance()
        mPokemonViewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            mPokemonViewModel.loadingHome.value = true
            mPokemonViewModel.addAllPokemonFromRes(pokemonService) //Build Pokemon PokemonInfo and PokemonTypeCrossRef Table
            mPokemonViewModel.getAllPokemonTypes(pokemonService) // Build Type Table
            mPokemonViewModel.loadingHome.value = false
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





