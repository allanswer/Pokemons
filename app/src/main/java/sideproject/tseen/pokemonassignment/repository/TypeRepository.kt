package sideproject.tseen.pokemonassignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import sideproject.tseen.pokemonassignment.model.type.TypeXX
import sideproject.tseen.pokemonassignment.persistence.PokemonDao

class TypeRepository(private val pokemonDao: PokemonDao) {
    val readAllData: LiveData<List<TypeXX>> = pokemonDao.readAllType()
    suspend fun addType(typeXX: TypeXX) {
        pokemonDao.insertTypes(typeXX)
    }
}