package sideproject.tseen.pokemonassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import sideproject.tseen.pokemonassignment.model.type.type

@Entity(tableName = "pokemon_info_table")
data class PokemonInfo (
    @PrimaryKey
    val id: Int,

    val name: String,
    val types: List<type>?,
)