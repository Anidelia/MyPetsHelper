package com.example.mypethelper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypethelper.databinding.FragmentPlaceListBinding

class ArticleList : Fragment(), ArticleAdapter.Listener {

    private lateinit var binding: FragmentPlaceListBinding
    val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    private lateinit var adapter: ArticleAdapter
    private var allArticlesList: MutableList<Article>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaceListBinding.inflate(inflater, container, false)
        adapter = ArticleAdapter(this, requireContext())
        binding.rcPlaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rcPlaces.adapter = adapter
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Разрешение еще не предоставлено, запросите его
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
        val firebase = FirebaseAPI()
        firebase.takeAll("Places") {
            val places = ParserArticles().parsPalces(it)
            allArticlesList = places
            adapter.createAll(places)
        }

        return binding.root
    }
    override fun onClick(partner: Article) {
        Log.i("Dibug1", "clicl")
        val mAct = (activity as MainActivity)
        mAct.ListenerForPlace(partner)
    }
}