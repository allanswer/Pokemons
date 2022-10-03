package sideproject.tseen.pokemonassignment.model.specie

import androidx.room.Entity
import androidx.room.PrimaryKey
import sideproject.tseen.pokemonassignment.model.type.TypeXX

@Entity(tableName = "pokemon_table")
data class Pokemon(
    @PrimaryKey
    val name: String,
    val url: String,
)