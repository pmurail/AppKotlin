package fr.nextu.murail_pierre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import fr.nextu.murail_pierre.databinding.FragmentPokemonTypeBinding

class PokemonTypeFragment : Fragment() {

    private lateinit var binding: FragmentPokemonTypeBinding
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
        binding = FragmentPokemonTypeBinding.inflate(inflater, container, false)

        binding.pokemonName.text = pokemonDetail.name
        Glide.with(this).load(pokemonDetail.sprites.frontDefault).into(binding.pokemonImage)
        binding.pokemonTypes.text = pokemonDetail.types.joinToString { it.type.name }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(pokemonDetail: PokemonDetail) =
            PokemonTypeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("POKEMON_DETAIL", pokemonDetail)
                }
            }
    }
}
