package sideproject.tseen.pokemonassignment.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sideproject.tseen.pokemonassignment.api.PokemonService
import sideproject.tseen.pokemonassignment.model.PokemonCardDetail
import sideproject.tseen.pokemonassignment.model.PokemonInfo
import sideproject.tseen.pokemonassignment.model.relations.PokemonTypeCrossRef
import sideproject.tseen.pokemonassignment.model.relations.TypeWithPokemons
import sideproject.tseen.pokemonassignment.model.response.PokemonList
import sideproject.tseen.pokemonassignment.model.response.PokemonSpecieResponse
import sideproject.tseen.pokemonassignment.model.response.PokemonTypeResponse
import sideproject.tseen.pokemonassignment.model.specie.Pokemon
import sideproject.tseen.pokemonassignment.model.type.TypeXX
import sideproject.tseen.pokemonassignment.model.type.type
import sideproject.tseen.pokemonassignment.persistence.PokemonDatabase
import sideproject.tseen.pokemonassignment.repository.PokemonInfoRepository
import sideproject.tseen.pokemonassignment.repository.PokemonRepository
import sideproject.tseen.pokemonassignment.repository.PokemonSpecieDetailRepository
import sideproject.tseen.pokemonassignment.repository.TypeRepository
import sideproject.tseen.pokemonassignment.util.Constants

class PokemonViewModel(application: Application): AndroidViewModel(application){
    private val TAG:String = javaClass.name
    private val readAllPokemon: LiveData<List<Pokemon>>
    private val readAllType: LiveData<List<TypeXX>>
    private val readAllPokemonInfo: LiveData<List<PokemonInfo>>
    private val readAllPokemonInfoOrderByType: LiveData<List<PokemonInfo>>
    private val pokemonSpecieDetailRepository: PokemonSpecieDetailRepository
    private val pokemonRepository: PokemonRepository
    private val typeRepository: TypeRepository
    private val pokemonInfoRepository: PokemonInfoRepository
    val loadingHome = mutableStateOf(false)
    val progress = mutableStateOf(0f)

    init {
        val pokemonDao = PokemonDatabase.getDatabase(application).pokemonDao()
        pokemonRepository = PokemonRepository(pokemonDao)
        typeRepository = TypeRepository(pokemonDao)
        pokemonInfoRepository= PokemonInfoRepository(pokemonDao)
        pokemonSpecieDetailRepository = PokemonSpecieDetailRepository(pokemonDao)
        readAllPokemon = pokemonRepository.readAllData
        readAllType = typeRepository.readAllData
        readAllPokemonInfo = pokemonInfoRepository.readAllData
        readAllPokemonInfoOrderByType= pokemonInfoRepository.readAllSortedData
    }
    fun insertPokemon(pokemon: Pokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository.addPokemon(pokemon)
        }
    }
    fun insertType(typeXX: TypeXX) {
        viewModelScope.launch(Dispatchers.IO) {
            typeRepository.addType(typeXX)
        }
    }
    fun insertPokemonInfo(pokemonInfo: PokemonInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonInfoRepository.addPokemonInfo(pokemonInfo)
        }
    }

    suspend fun addPokemonTypeCrossRef(pokemonTypeCrossRef: PokemonTypeCrossRef) {
        pokemonInfoRepository.insertPokemonTypeCrossRef(pokemonTypeCrossRef)
    }

    fun getAllPokemon(): LiveData<List<Pokemon>>{
        return pokemonRepository.readAllData
    }

    fun getAllTypes(): LiveData<List<TypeXX>>{
        return readAllType
    }


    fun getPokemonsOfType(pokemonType: String): LiveData<List<TypeWithPokemons>>{
        return pokemonInfoRepository.getPokemonsOfType(pokemonType)
    }

    fun getPokemonInfo(pokemonName: String): LiveData<List<PokemonInfo>>{
        return pokemonInfoRepository.getPokemonInfo(pokemonName)
    }

    fun getPokemonSpecieCardDetail(pokemonName: String): LiveData<List<PokemonCardDetail>>{
        return pokemonSpecieDetailRepository.getPokemonCardDetail(pokemonName)
    }

    //

    /* Send api request and build Pokemon and PokemonInfo DB */
    suspend fun addAllPokemonFromRes(pokemonService: PokemonService) {
        Log.d(TAG, "getPokemonRes: Start to send api request and build Pokemon, PokemonInfo and PokemonTypeCrossRef DB...")
        val response = pokemonService.getPokemons();
        if(response.isSuccessful) {
            val data: PokemonList = response.body()!!
            for(pokemon in data.results) {
                insertPokemon(pokemon)
                addPokeInfo(pokemonService, pokemon.name)
                addPokemonSpecieInfo(pokemonService, pokemon.name)
                progress.value += 0.7f / data.results.size
            }
            progress.value = 0.7f
        }
        Log.d(TAG, "getPokemonRes: Pokemon PokemonInfo and PokemonTypeCrossRef tables are saved")
    }


    /* Send api request and save each Pokemon Information to DB*/
    suspend fun addPokeInfo(pokemonService: PokemonService, name:String) {
        val response = pokemonService.getPokemonInfoWithType(name);
        if(response.isSuccessful) {
            val data: type = response.body()!!

            data.let { pokemonInfo ->
                //1NF
                val newPokemonInfo = PokemonInfo(
                    pokemonName = pokemonInfo.name,
                    id = pokemonInfo.id,
                    imageUrl = pokemonInfo.sprites.other.official_artwork.front_default,
                    pokemonType = pokemonInfo.types.map { it.type.typeName }
                )
                insertPokemonInfo(newPokemonInfo)
                insertPokemonTypeCrossRef(newPokemonInfo)
            }
        }
    }

    private suspend fun insertPokemonTypeCrossRef(pokemonInfo: PokemonInfo){
        pokemonInfo.pokemonType.map {
            val newPokemonTypeCrossRef = PokemonTypeCrossRef(
                pokemonName = pokemonInfo.pokemonName,
                typeName = it
            )
            addPokemonTypeCrossRef(newPokemonTypeCrossRef)
        }
    }

    /* get types of all Pokemon */
    suspend fun getAllPokemonTypes(pokemonService: PokemonService) {
        Log.d(TAG, "getAllPokemonTypes: Start to get types of all Pokemon...")
        val response = pokemonService.getAllPokemonType()
        if(response.isSuccessful) {
            val data: PokemonTypeResponse = response.body()!!
            for(type in data.results) {
                progress
                insertType(type)
                progress.value += 0.3f / data.results.size
            }
        }
        progress.value = 1f
        Log.d(TAG, "getAllPokemonTypes: Types(TypeXX) table saved")
    }

    /* Add specie information like evolution information into DB*/
    private suspend fun addPokemonSpecieInfo(pokemonService: PokemonService, name: String) {
        val response = pokemonService.getPokemonSepcieInfo(name)
        if(response.isSuccessful) {
            val data: PokemonSpecieResponse = response.body()!!

            val newPokemonCardDetail = PokemonCardDetail(
                pokemonName = data.name,
                evolveFrom = data.evolves_from_species?.name,
                desc = data.flavor_text_entries.firstOrNull{it.language.name == Constants.LANGUAGE}?.flavor_text.toString()
            )
            pokemonSpecieDetailRepository.addPokemonCardDetails(newPokemonCardDetail)
        }
    }



}