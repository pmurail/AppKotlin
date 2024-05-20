package fr.nextu.murail_pierre

import android.os.Parcelable
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object PokeApiClient {
    private val client = OkHttpClient()
    private val gson = Gson()

    suspend fun fetchPokemonDetails(pokemonId: Int): PokemonDetail {
        val request = Request.Builder()
            .url("https://pokeapi.co/api/v2/pokemon/$pokemonId")
            .build()

        return withContext(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val responseBody = response.body?.string()
                gson.fromJson(responseBody, PokemonDetail::class.java)
            }
        }
    }

    suspend fun fetchFirst30Pokemons(): List<PokemonDetail> = coroutineScope {
        val url = "https://pokeapi.co/api/v2/pokemon?limit=30"
        val pokemonListResponse = makeRequest(url)
        val pokemonList = gson.fromJson(pokemonListResponse, PokemonListResponse::class.java)

        pokemonList.results.map { pokemon ->
            async { fetchPokemonDetails(pokemon.url) }
        }.map { it.await() }
    }

    private suspend fun fetchPokemonDetails(url: String): PokemonDetail {
        val response = makeRequest(url)
        return gson.fromJson(response, PokemonDetail::class.java)
    }

    private suspend fun makeRequest(url: String): String = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url(url)
                .get()
                .build()
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    response.body?.string() ?: "Failed to retrieve body"
                } else {
                    Log.d("NetworkClient", "Failed with status code: ${response.code}")
                    throw Exception("Network call failed with status code: ${response.code}")
                }
            }
        } catch (e: Exception) {
            Log.d("NetworkClient", "Error: ${e.localizedMessage}")
            throw e
        }
    }
}

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonSimple>
)

data class PokemonSimple(
    val name: String,
    val url: String
)

@Parcelize
data class PokemonDetail(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>
) : Parcelable

@Parcelize
data class PokemonSprites(
    @SerializedName("front_default") val frontDefault: String
) : Parcelable

@Parcelize
data class PokemonType(
    val type: Type
) : Parcelable

@Parcelize
data class Type(
    val name: String
) : Parcelable

@Parcelize
data class PokemonStat(
    @SerializedName("base_stat") val baseStat: Int,
    val stat: Stat
) : Parcelable

@Parcelize
data class Stat(
    val name: String
) : Parcelable
