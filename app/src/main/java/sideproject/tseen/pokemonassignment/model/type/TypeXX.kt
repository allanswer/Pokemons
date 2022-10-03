package sideproject.tseen.pokemonassignment.model.type

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TypeXX(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("name")
    val typeName: String,

    val url: String
)