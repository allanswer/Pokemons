package sideproject.tseen.pokemonassignment.model.response

import sideproject.tseen.pokemonassignment.model.specie.Pokemon

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Pokemon>
)

