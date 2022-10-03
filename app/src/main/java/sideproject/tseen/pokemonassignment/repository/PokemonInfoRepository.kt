package sideproject.tseen.pokemonassignment.repository

import androidx.lifecycle.LiveData
import sideproject.tseen.pokemonassignment.model.PokemonInfo
import sideproject.tseen.pokemonassignment.model.relations.PokemonTypeCrossRef
import sideproject.tseen.pokemonassignment.model.relations.TypeWithPokemons
import sideproject.tseen.pokemonassignment.persistence.PokemonDao

class PokemonInfoRepository (private val pokemonDao: PokemonDao){
    val readAllData: LiveData<List<PokemonInfo>> = pokemonDao.readPokemonInfo()
    val readAllSortedData: LiveData<List<PokemonInfo>> = pokemonDao.readPokemonsOrderByType()
    val readAllPokemonTypeCrossRef: LiveData<List<PokemonTypeCrossRef>> = pokemonDao.readPokemonTypeCrossRef()

    suspend fun addPokemonInfo(pokemonInfo: PokemonInfo) {
        pokemonDao.insertPokemonInfo(pokemonInfo)
    }

    suspend fun insertPokemonTypeCrossRef(pokemonTypeCrossRef: PokemonTypeCrossRef) {
        pokemonDao.insertPokemonInfoTypeCrossRef(pokemonTypeCrossRef)
    }

    fun getPokemonsOfType(pokemonType: String): LiveData<List<TypeWithPokemons>>{
        return pokemonDao.getPokemonsOfType(pokemonType)
    }

    fun getPokemonInfo(pokemonName: String): LiveData<List<PokemonInfo>>{
        return pokemonDao.getPokemonInfo(pokemonName)
    }

    suspend fun getPokemonOrderByType(): LiveData<List<PokemonInfo>> {
        return readAllSortedData
    }
}