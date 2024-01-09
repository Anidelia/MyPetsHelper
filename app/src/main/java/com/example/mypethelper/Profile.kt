package com.example.mypethelper

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypethelper.Adapters.ArticleAdapter
import com.example.mypethelper.DataClasses.Article
import com.example.mypethelper.DataClasses.ArticleData
import com.example.mypethelper.Parsers.ParceUsers
import com.example.mypethelper.Parsers.ParserArticles
import com.example.mypethelper.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class Profile : Fragment(), ArticleAdapter.Listener {

    // Инициализация переменных
    private lateinit var binding: FragmentProfileBinding
    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Надуваем макет для фрагмента
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Инициализация адаптера и установка его в RecyclerView
        adapter = ArticleAdapter(this, requireContext())
        binding.rcPlas.layoutManager = LinearLayoutManager(requireContext())
        binding.rcPlas.adapter = adapter

        // Инициализация FirebaseAPI и получение данных о пользователе из SharedPreferences
        val firebase = FirebaseAPI()
        val sharedPreferences = requireContext().getSharedPreferences("SPDB", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getInt("id", 1)

        // Получение данных пользователя из Firebase и загрузка изображений с помощью Picasso
        firebase.takeOne("Users", uid) {
            val user = ParceUsers().parsUser(it)
            FirebaseAPI().getPicLogo(user.ava) { it2 ->
                Picasso.get().load(it2).into(binding.imageView5)
            }
            FirebaseAPI().getPicLogo(user.fon) { it3 ->
                Picasso.get().load(it3).into(binding.imageView)
            }

            val shaderPref = requireContext().getSharedPreferences("SPDB", Context.MODE_PRIVATE)
            val count = shaderPref.getInt("count", 0)
            val favoriteList = mutableListOf<Int>()

            for (i in 0 until count) {
                val uid = shaderPref.getInt("articleID${i}", 0)
                favoriteList.add(uid)
            } //rcPlas
            binding.textView5.text = "${user.name} ${user.surname}\nСохранено статей: $count"
            firebase.takeAll("Articles") {
                val articles = ParserArticles().parsArticles(it)
                val allArticlesList = mutableListOf<Article>()
                for (artic in articles) {
                    if (checkById(favoriteList, artic.id)) {
                        allArticlesList.add(artic)
                    }
                }
                adapter.createAll(allArticlesList)
            }
        }


        // Возврат корневого элемента макета
        return binding.root
    }

    private fun checkById (mas: MutableList<Int>, id: Int): Boolean {
        for (arti in mas) {
            if (arti == id){
                return true
            }
        }
        return false
    }

    override fun onClick(partner: Article) {
        TODO("Not yet implemented")
    }
}
