package sideproject.tseen.pokemonassignment.repository

import androidx.lifecycle.LiveData
import sideproject.tseen.pokemonassignment.model.specie.Pokemon
import sideproject.tseen.pokemonassignment.persistence.PokemonDao

class PokemonRepository(private val pokemonDao: PokemonDao){
    val readAllData: LiveData<List<Pokemon>> = pokemonDao.readAllPokemon()
    suspend fun addPokemon(pokemon: Pokemon) {
        pokemonDao.insertPokemon(pokemon)
    }
}