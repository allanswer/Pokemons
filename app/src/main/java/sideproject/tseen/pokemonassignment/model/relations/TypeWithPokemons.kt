package sideproject.tseen.pokemonassignment.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import sideproject.tseen.pokemonassignment.model.PokemonInfo
import sideproject.tseen.pokemonassignment.model.specie.Pokemon
import sideproject.tseen.pokemonassignment.model.type.TypeXX

data class TypeWithPokemons(
    @Embedded val type: TypeXX,
    @Relation(
        parentColumn = "typeName",
        entityColumn = "pokemonName",
        associateBy = Junction(PokemonTypeCrossRef::class)
    )
    val pokemons: List<PokemonInfo>
)
