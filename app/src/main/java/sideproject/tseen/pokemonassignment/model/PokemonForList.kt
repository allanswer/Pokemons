package sideproject.tseen.pokemonassignment.model

import androidx.room.PrimaryKey

data class PokemonForList(
    val name: String,

    @PrimaryKey
    val id: Int,

    val Image: String? =null,
)
