package sideproject.tseen.pokemonassignment.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import sideproject.tseen.pokemonassignment.model.PokemonInfo
import sideproject.tseen.pokemonassignment.model.specie.Pokemon
import sideproject.tseen.pokemonassignment.model.type.TypeXX

data class PokemonWithTypes(
    @Embedded val pokemonInfo: PokemonInfo,
    @Relation(
        parentColumn = "pokemonName",
        entityColumn = "typeName",
        associateBy = Junction(PokemonTypeCrossRef::class)
    )
    val types: List<TypeXX>
)
