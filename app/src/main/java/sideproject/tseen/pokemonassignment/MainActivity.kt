package sideproject.tseen.pokemonassignment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import sideproject.tseen.pokemonassignment.api.PokemonService
import sideproject.tseen.pokemonassignment.model.response.PokemonList
import sideproject.tseen.pokemonassignment.ui.theme.PokemonAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPokemonRes();
        setContent {
            PokemonAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokemonAssignmentTheme {
        Greeting("Android")
    }
}

fun getPokemonRes() {
    GlobalScope.launch(Dispatchers.IO) {
        val pokemonService = PokemonService.getInstance()
        val response = pokemonService.getPokemons();
        if(response.isSuccessful) {
            val data: PokemonList = response.body()!!
            Log.d("a", data.count.toString())
            Log.d("a", data.results.size.toString());
        }
    }
}