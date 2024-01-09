package com.example.mypethelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypethelper.DataClasses.PetData
import com.example.mypethelper.databinding.FragmentPetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class Pet : Fragment() {
    private lateinit var binding: FragmentPetBinding
    private lateinit var petAdapter: PetAdapter
    private lateinit var petDataManager: PetDataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализируйте petDataManager перед использованием
        petDataManager = PetDataManager(requireContext())

        // Инициализируйте petAdapter перед использованием
        petAdapter = PetAdapter(petDataManager.loadPetList().toMutableList())

        binding.recyclerViewPets.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPets.adapter = petAdapter

        binding.buttonAddMain.setOnClickListener {
            showAddPetBottomSheet()
        }
    }

    private fun showAddPetBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_pet_add, null)
        dialog.setCancelable(true)
        dialog.setContentView(view)

        view.findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            // Получите введенные данные пользователя
            val petName = view.findViewById<EditText>(R.id.editTextNick).text.toString()
            val petType = view.findViewById<EditText>(R.id.editTextVid).text.toString()
            val petAge = view.findViewById<EditText>(R.id.editTextAge).text.toString()

            // Создайте экземпляр Pet и добавьте его в список
            val pet = PetData(name = petName, type = petType, age = petAge)
            petAdapter.addPet(pet)

            // Сохранение обновленных данных
            petDataManager.savePetList(petAdapter.getPetList())

            // Закройте BottomSheetDialog
            dialog.dismiss()
        }

        dialog.show()
    }
}

