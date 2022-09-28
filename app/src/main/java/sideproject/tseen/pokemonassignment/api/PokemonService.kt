package sideproject.tseen.pokemonassignment.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import sideproject.tseen.pokemonassignment.model.response.PokemonList

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int = 151
    ): Response<PokemonList>

    companion object {
        var pokemonService: PokemonService? = null

        fun getInstance() : PokemonService {
            if (pokemonService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                pokemonService = retrofit.create(PokemonService::class.java)
            }
            return pokemonService!!
        }
    }
}


