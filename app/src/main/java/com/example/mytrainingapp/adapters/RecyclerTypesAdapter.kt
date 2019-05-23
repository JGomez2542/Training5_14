package com.example.mytrainingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrainingapp.R
import com.example.mytrainingapp.data.entities.Entities
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_animal.*
import kotlinx.android.synthetic.main.item_person.*
import kotlinx.android.synthetic.main.item_planet.*
import java.lang.IllegalArgumentException

class RecyclerTypesAdapter(private val recyclerTypes: List<Entities>,
                           val clickListener: OnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, res: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return when (res) {
            R.layout.item_person -> {
                val itemView = inflater.inflate(R.layout.item_person, viewGroup, false)
                PersonViewHolder(itemView)
            }
            R.layout.item_animal -> {
                val itemView = inflater.inflate(R.layout.item_animal, viewGroup, false)
                AnimalViewHolder(itemView)
            }
            R.layout.item_planet -> {
                val itemView = inflater.inflate(R.layout.item_planet, viewGroup, false)
                PlanetViewHolder(itemView)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount() = recyclerTypes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = recyclerTypes[position]
        when (type) {
            is Entities.Person -> (holder as PersonViewHolder).bind(type)
            is Entities.Animal -> (holder as AnimalViewHolder).bind(type)
            is Entities.Planet -> (holder as PlanetViewHolder).bind(type)
        }
        holder.itemView.setOnClickListener { clickListener.onItemClicked(type) }
    }

    override fun getItemViewType(position: Int): Int {
        return when (recyclerTypes[position]) {
            is Entities.Animal -> R.layout.item_animal
            is Entities.Planet -> R.layout.item_planet
            is Entities.Person -> R.layout.item_person
        }
    }

    class PersonViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(person: Entities.Person) {
            tvPersonName.text = person.name
        }
    }

    class AnimalViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(animal: Entities.Animal) {
            tvAnimalName.text = animal.name
        }
    }

    class PlanetViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(planet: Entities.Planet) {
            tvPlanetName.text = planet.planetName
        }
    }

    interface OnClickListener {
        fun onItemClicked(item: Entities)
    }
}