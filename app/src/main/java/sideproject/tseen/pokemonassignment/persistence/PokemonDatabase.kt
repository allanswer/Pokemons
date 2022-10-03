package sideproject.tseen.pokemonassignment.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sideproject.tseen.pokemonassignment.model.Converters
import sideproject.tseen.pokemonassignment.model.PokemonCardDetail
import sideproject.tseen.pokemonassignment.model.PokemonInfo
import sideproject.tseen.pokemonassignment.model.relations.PokemonTypeCrossRef
import sideproject.tseen.pokemonassignment.model.specie.Pokemon
import sideproject.tseen.pokemonassignment.model.type.TypeXX

@Database(
    entities = [
        Pokemon::class,
        PokemonTypeCrossRef::class,
        PokemonInfo::class,
        PokemonCardDetail::class,
        TypeXX::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters::class)

abstract class PokemonDatabase: RoomDatabase(){
    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "pokemon_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}