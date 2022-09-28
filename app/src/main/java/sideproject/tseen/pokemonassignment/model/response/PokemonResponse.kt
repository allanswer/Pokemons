package sideproject.tseen.pokemonassignment.model.response

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<PokemonResult>
)

