package sideproject.tseen.pokemonassignment.repository

import androidx.lifecycle.LiveData
import sideproject.tseen.pokemonassignment.model.PokemonCardDetail
import sideproject.tseen.pokemonassignment.persistence.PokemonDao

class PokemonSpecieDetailRepository (private val pokemonDao: PokemonDao){
    suspend fun addPokemonCardDetails(pokemonCardDetail: PokemonCardDetail) {
        pokemonDao.insertPokemonCardDetails(pokemonCardDetail)
    }
    fun getPokemonCardDetail(pokemonName: String): LiveData<List<PokemonCardDetail>>{
        return pokemonDao.getPokemonCardDetail(pokemonName)
    }
}