package com.example.mypethelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypethelper.DataClasses.PetData


class PetAdapter(private val petList: MutableList<PetData>) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textType: TextView = itemView.findViewById(R.id.textVid)
        val textAge: TextView = itemView.findViewById(R.id.tt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pet, parent, false)
        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = petList[position]
        holder.textTitle.text = pet.name
        holder.textType.text = "Вид: ${pet.type}"
        holder.textAge.text = "Возраст: ${pet.age}"  // Предполагается, что у питомца есть поле "age"
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    fun addPet(pet: PetData) {
        petList.add(pet)
        notifyDataSetChanged()
    }
    fun removePet(pet: PetData) {
        petList.remove(pet)
        notifyDataSetChanged()
    }

    fun getPetList(): List<PetData> {
        return petList
    }
}
