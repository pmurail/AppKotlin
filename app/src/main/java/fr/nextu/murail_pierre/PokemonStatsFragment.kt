package fr.nextu.murail_pierre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.nextu.murail_pierre.databinding.FragmentPokemonStatsBinding

class PokemonStatsFragment : Fragment() {

    private lateinit var binding: FragmentPokemonStatsBinding
    private lateinit var pokemonDetail: PokemonDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokemonDetail = it.getParcelable("POKEMON_DETAIL")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonStatsBinding.inflate(inflater, container, false)

        pokemonDetail.stats.forEach { stat ->
            when (stat.stat.name) {
                "hp" -> binding.pokemonHp.text = "HP: ${stat.baseStat}"
                "attack" -> binding.pokemonAttack.text = "Attack: ${stat.baseStat}"
                "defense" -> binding.pokemonDefense.text = "Defense: ${stat.baseStat}"
                "speed" -> binding.pokemonSpeed.text = "Speed: ${stat.baseStat}"
                "special-attack" -> binding.pokemonSpecialAttack.text = "Special Attack: ${stat.baseStat}"
                "special-defense" -> binding.pokemonSpecialDefense.text = "Special Defense: ${stat.baseStat}"
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(pokemonDetail: PokemonDetail) =
            PokemonStatsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("POKEMON_DETAIL", pokemonDetail)
                }
            }
    }
}
