package sideproject.tseen.pokemonassignment.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import sideproject.tseen.pokemonassignment.model.response.PokemonList
import sideproject.tseen.pokemonassignment.model.response.PokemonSpecieResponse
import sideproject.tseen.pokemonassignment.model.response.PokemonTypeResponse
import sideproject.tseen.pokemonassignment.model.type.type
import sideproject.tseen.pokemonassignment.util.Constants

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int = 151
    ): Response<PokemonList>

    @GET("type")
    suspend fun getAllPokemonType(
    ): Response<PokemonTypeResponse>

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSepcieInfo(
        @Path("name") name: String
    ): Response<PokemonSpecieResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfoWithType(
        @Path("name") name: String
    ): Response<type>

    companion object {
        var pokemonService: PokemonService? = null

        fun getInstance() : PokemonService {
            if (pokemonService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                pokemonService = retrofit.create(PokemonService::class.java)
            }
            return pokemonService!!
        }
    }
}


