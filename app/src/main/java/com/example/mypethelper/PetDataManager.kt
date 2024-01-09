package com.example.mypethelper

import android.content.Context
import com.example.mypethelper.DataClasses.PetData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PetDataManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("PetPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun savePetList(petList: List<PetData>) {
        val petListJson = gson.toJson(petList)
        sharedPreferences.edit().putString("petList", petListJson).apply()
    }

    fun loadPetList(): List<PetData> {
        val petListJson = sharedPreferences.getString("petList", null)
        return if (petListJson != null) {
            val typeToken = object : TypeToken<List<PetData>>() {}.type
            gson.fromJson(petListJson, typeToken)
        } else {
            emptyList()
        }
    }
}
