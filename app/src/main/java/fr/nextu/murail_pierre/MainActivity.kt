package fr.nextu.murail_pierre

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import fr.nextu.murail_pierre.databinding.ActivityMainBinding
import fr.nextu.murail_pierre.entities.PokemonAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupRecyclerView()
        CoroutineScope(Dispatchers.Main).launch {
            loadPokemons()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        pokemonAdapter = PokemonAdapter(emptyList()) { pokemon ->
            showPokemonDetails(pokemon)
        }
        binding.recyclerView.adapter = pokemonAdapter
    }

    private suspend fun loadPokemons() {
        val pokemons = withContext(Dispatchers.IO) {
            PokeApiClient.fetchFirst30Pokemons()
        }
        pokemonAdapter.pokemons = pokemons
        pokemonAdapter.notifyDataSetChanged()
    }

    private fun showPokemonDetails(pokemon: PokemonDetail) {
        // Afficher les détails du Pokémon ici
        // Vous pouvez utiliser un Intent pour démarrer une nouvelle activité ou afficher un dialogue, par exemple
        Toast.makeText(this, "Selected: ${pokemon.name}", Toast.LENGTH_SHORT).show()
    }
}
