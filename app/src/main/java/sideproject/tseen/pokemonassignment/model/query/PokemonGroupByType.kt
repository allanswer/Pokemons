package sideproject.tseen.pokemonassignment.model.query

data class PokemonGroupByType(
    val pokemonType: String,
    val pokemonName: String,
    val imageUrl: String,
    val count: Int,
)