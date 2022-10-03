package sideproject.tseen.pokemonassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonCardDetail(
    @PrimaryKey val pokemonName: String,
    val evolveFrom: String?,
    val desc: String?
)
