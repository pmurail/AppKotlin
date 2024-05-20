package fr.nextu.murail_pierre

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import fr.nextu.murail_pierre.databinding.ActivityPokemonDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonId = intent.getIntExtra("POKEMON_ID", -1)

        if (pokemonId != -1) {
            // Fetch Pokemon details and pass them to fragments
            lifecycleScope.launch {
                val pokemonDetails = fetchPokemonDetails(pokemonId)
                loadFragment(PokemonTypeFragment.newInstance(pokemonDetails), binding.fragmentContainer1.id)
                loadFragment(PokemonStatsFragment.newInstance(pokemonDetails), binding.fragmentContainer2.id)
            }
        }
    }

    private suspend fun fetchPokemonDetails(pokemonId: Int): PokemonDetail {
        return withContext(Dispatchers.IO) {
            PokeApiClient.fetchPokemonDetails(pokemonId)
        }
    }

    private fun loadFragment(fragment: Fragment, containerId: Int) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment)
        fragmentTransaction.commit()
    }
}
