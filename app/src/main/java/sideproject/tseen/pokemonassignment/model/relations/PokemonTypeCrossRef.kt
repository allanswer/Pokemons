package sideproject.tseen.pokemonassignment.model.relations

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonName", "typeName"])
data class PokemonTypeCrossRef(
    val pokemonName: String,
    val typeName: String
)