package com.example.mypethelper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypethelper.Adapters.ArticleAdapter
import com.example.mypethelper.DataClasses.Article
import com.example.mypethelper.Parsers.ParserArticles
import com.example.mypethelper.databinding.FragmentArticleListBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ArticleList : Fragment(), ArticleAdapter.Listener {

    private lateinit var binding: FragmentArticleListBinding
    private lateinit var adapter: ArticleAdapter
    private var allArticlesList: MutableList<Article>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        adapter = ArticleAdapter(this, requireContext())
        binding.rcPlaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rcPlaces.adapter = adapter

        val firebase = FirebaseAPI()
        firebase.takeAll("Articles") {
            val articles = ParserArticles().parsArticles(it)
            allArticlesList = articles
            adapter.createAll(articles)
        }
        var editor = binding.root.findViewById<androidx.appcompat.widget.AppCompatAutoCompleteTextView>(R.id.searchView)
        editor.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(editor.windowToken, 0)
                editor.clearFocus()
                search(editor.text.toString())
                return@OnKeyListener true
            }
            false
        })

        return binding.root
    }


    private fun search(str: String) {
        val newList = mutableListOf<Article>()
        allArticlesList?.forEach {
            if (it.title.contains(str, ignoreCase = true)) {
                newList.add(it)
            }
        }
        adapter.createAll(newList)
    }

    override fun onClick(article: Article) {
        Log.i("Dibug1", "clicl")
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_article_full, null)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        view.findViewById<TextView>(R.id.textContent).text = article.content
        view.findViewById<TextView>(R.id.textTitle).text = article.title
        val shaderPref = requireContext().getSharedPreferences("SPDB", Context.MODE_PRIVATE)
        val editor = shaderPref.edit()
        val count = shaderPref.getInt("count", 0)
        for (i in 0 until count) {
            val uid = shaderPref.getInt("articleID${i}", 0)
            if (uid == article.id) {
                view.findViewById<ImageView>(R.id.imageViewLike).visibility = View.INVISIBLE
                break
            }
        }
        view.findViewById<ImageView>(R.id.imageViewLike).setOnClickListener {
            var count = shaderPref.getInt("count", 0)
            editor.apply{
                putInt("articleID${count}", article.id)
                count++
                putInt("count", count)
                apply()
            }
            view.findViewById<ImageView>(R.id.imageViewLike).visibility = View.INVISIBLE
        }

        val storageReference = FirebaseStorage.getInstance().getReference()
        val imageReference = storageReference.child(article.image)

        imageReference.downloadUrl.addOnSuccessListener { uri ->
            // Загрузка изображения с использованием Picasso
            Picasso.get().load(uri.toString()).into(view.findViewById<ImageView>(R.id.textImage))
        }
        dialog.show()
    }
}