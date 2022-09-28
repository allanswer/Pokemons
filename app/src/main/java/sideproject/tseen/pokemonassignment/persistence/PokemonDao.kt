package sideproject.tseen.pokemonassignment.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sideproject.tseen.pokemonassignment.model.PokemonInfo

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPokemonInfo(pokemonInfo: PokemonInfo)

    @Query("SELECT * FROM pokemon_info_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<PokemonInfo>>
}