package com.example.mypethelper

import ParceUsers
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypethelper.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso

class Profile : Fragment(), ArticleAdapterUser.Listener {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var adapter: ArticleAdapterUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        adapter = ArticleAdapterUser(requireContext(), this)
        binding.rcPlas.layoutManager = LinearLayoutManager(requireContext())
        binding.rcPlas.adapter=adapter
        val firebase = FirebaseAPI()
        val sharedPreferences = requireContext().getSharedPreferences("SPDB", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getInt("id", 1)
        firebase.takeOne("Users", uid) {
            val user = ParceUsers().parsUser(it)
            FirebaseAPI().getPicLogo(user.ava) { it2 ->
                Picasso.get().load(it2).into(binding.imageView5)
            }
            FirebaseAPI().getPicLogo(user.fon) { it3 ->
                Picasso.get().load(it3).into(binding.imageView)
            }
            binding.textView5.text = "${user.name} ${user.surname}\nСохранено статей: ${user.visits.count()}"
            binding.textView11.text = user.score.toString()
            user.visits.forEach { it4 ->
                FirebaseAPI().takeOne("Articles", it4.placeID) { it5 ->
                    adapter.createElement(it4, it5)
                }
            }
        }
        return binding.root
    }

}