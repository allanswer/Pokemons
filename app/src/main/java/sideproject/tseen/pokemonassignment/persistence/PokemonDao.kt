package sideproject.tseen.pokemonassignment.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import sideproject.tseen.pokemonassignment.model.PokemonCardDetail
import sideproject.tseen.pokemonassignment.model.PokemonInfo
import sideproject.tseen.pokemonassignment.model.relations.PokemonTypeCrossRef
import sideproject.tseen.pokemonassignment.model.relations.TypeWithPokemons
import sideproject.tseen.pokemonassignment.model.specie.Pokemon
import sideproject.tseen.pokemonassignment.model.type.TypeXX

@Dao
interface PokemonDao {

//
//    @Query("SELECT * FROM pokemon_info_table")
//    fun readAllData(): LiveData<List<PokemonInfo>>
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addPokemonInfo(pokemonInfo: PokemonInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypes(typeXX: TypeXX)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInfo(pokemonInfo: PokemonInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInfoTypeCrossRef(crossRef: PokemonTypeCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonCardDetails(pokemonCardDetail: PokemonCardDetail)


    @Query("SELECT * FROM pokemon_info_table ORDER BY pokemonName ASC")
    fun readPokemonInfo(): LiveData<List<PokemonInfo>>

    @Query("SELECT * FROM typexx ORDER BY typeName ASC")
    fun readAllType(): LiveData<List<TypeXX>>

    @Query("SELECT * FROM pokemon_table ORDER BY name ASC")
    fun readAllPokemon(): LiveData<List<Pokemon>>

    @Query("SELECT * FROM pokemontypecrossref")
    fun readPokemonTypeCrossRef(): LiveData<List<PokemonTypeCrossRef>>

    @Query("SELECT * FROM pokemon_info_table WHERE pokemonName = :pokemonName")
    fun getPokemonInfo(pokemonName: String): LiveData<List<PokemonInfo>>

    @Query("SELECT * FROM pokemoncarddetail WHERE pokemonName = :pokemonName")
    fun getPokemonCardDetail(pokemonName: String): LiveData<List<PokemonCardDetail>>

    @Transaction
    @Query("SELECT * FROM typexx WHERE typeName == :typeName")
    fun getPokemonsOfType(typeName: String): LiveData<List<TypeWithPokemons>>
//    @Query("SELECT * FROM pokemon_table WHERE name = :name")
//    suspend fun getTypesOfPokemon(name: String): LiveData<List<PokemonWithTypes>>

    @Query("SELECT pokemonName, id, imageUrl, pokemonType FROM pokemon_info_table ORDER BY pokemonType")
    fun readPokemonsOrderByType(): LiveData<List<PokemonInfo>>

    @Query("SELECT pokemonName, id, imageUrl, pokemonType FROM pokemon_info_table ORDER BY pokemonType")
    fun readPokemonsInfoByName(): LiveData<List<PokemonInfo>>


//    @Query("SELECT * FROM pokemon_table WHERE name = :name")
//    suspend fun getTypesOfPokemon(name: String): List<PokemonWithTypes>


}