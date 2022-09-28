package sideproject.tseen.pokemonassignment.repository

import androidx.lifecycle.LiveData
import sideproject.tseen.pokemonassignment.model.PokemonInfo
import sideproject.tseen.pokemonassignment.persistence.PokemonDao

class PokemonRepository (private val pokemonDao: PokemonDao){
    val readAllData: LiveData<List<PokemonInfo>> = pokemonDao.readAllData()

    suspend fun addPokemon(pokemonInfo: PokemonInfo) {
        pokemonDao.addPokemonInfo(pokemonInfo)
    }
}