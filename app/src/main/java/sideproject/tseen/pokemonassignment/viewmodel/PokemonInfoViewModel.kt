package sideproject.tseen.pokemonassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sideproject.tseen.pokemonassignment.model.PokemonInfo
import sideproject.tseen.pokemonassignment.persistence.PokemonDatabase
import sideproject.tseen.pokemonassignment.repository.PokemonRepository

class PokemonInfoViewModel(application: Application): AndroidViewModel(application){
    private val readAllData: LiveData<List<PokemonInfo>>
    private val repository: PokemonRepository
    init {
        val pokemonDao = PokemonDatabase.getDatabase(application).pokemonDao()
        repository = PokemonRepository(pokemonDao)
        readAllData = repository.readAllData
    }
    fun addPokemon(pokemonInfo: PokemonInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPokemon(pokemonInfo)
        }
    }

}