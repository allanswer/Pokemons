package sideproject.tseen.pokemonassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "pokemon_info_table")
data class PokemonInfo (
    @PrimaryKey val pokemonName: String,
    val id: Int,
    val imageUrl: String,
    val pokemonType: List<String>
    //    @SerializedName("type") val pokemonType: String,
)

class Converters{
    @TypeConverter
    fun fromType(list: List<String?>?): String? {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toType(list: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson<List<String>>(list, type)
    }
}