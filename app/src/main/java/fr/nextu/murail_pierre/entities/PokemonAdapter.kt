package fr.nextu.murail_pierre.entities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.nextu.murail_pierre.PokemonDetail
import fr.nextu.murail_pierre.PokemonDetailActivity
import fr.nextu.murail_pierre.R

class PokemonAdapter(
    var pokemons: List<PokemonDetail>,
    private val onItemClicked: (PokemonDetail) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.textView.text = pokemon.name
        Glide.with(holder.imageView.context)
            .load(pokemon.sprites.frontDefault)
            .into(holder.imageView)
        holder.button.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PokemonDetailActivity::class.java).apply {
                putExtra("POKEMON_ID", pokemon.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = pokemons.size

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.pokemon_image)
        val textView: TextView = view.findViewById(R.id.pokemon_name)
        val button: Button = view.findViewById(R.id.btn_view_details)
    }
}

