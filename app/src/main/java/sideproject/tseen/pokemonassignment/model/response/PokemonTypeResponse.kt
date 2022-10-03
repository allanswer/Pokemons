package sideproject.tseen.pokemonassignment.model.response

import sideproject.tseen.pokemonassignment.model.specie.Pokemon
import sideproject.tseen.pokemonassignment.model.type.TypeXX

data class PokemonTypeResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<TypeXX>
)