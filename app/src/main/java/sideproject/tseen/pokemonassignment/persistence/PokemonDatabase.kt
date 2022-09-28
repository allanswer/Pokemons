package sideproject.tseen.pokemonassignment.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sideproject.tseen.pokemonassignment.model.PokemonInfo

@Database(entities = [PokemonInfo::class], version = 1, exportSchema = false)
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