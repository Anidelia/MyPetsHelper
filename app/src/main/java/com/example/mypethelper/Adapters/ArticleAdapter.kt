package com.example.mypethelper.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mypethelper.DataClasses.Article
import com.example.mypethelper.R
import com.example.mypethelper.databinding.ArticleBinding
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class ArticleAdapter(val listener: Listener, val context: Context): RecyclerView.Adapter<ArticleAdapter.ArticleHolder>() {
    private var ArticleList=ArrayList<Article>()

    class ArticleHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ArticleBinding.bind(item)
        @RequiresApi(Build.VERSION_CODES.Q)
        fun bind(article: Article, listener: Listener, context: Context) = with(binding){
            val storageReference = FirebaseStorage.getInstance().getReference()
            val imageReference = storageReference.child(article.image)

            imageReference.downloadUrl.addOnSuccessListener { uri ->
                // Загрузка изображения с использованием Picasso
                Picasso.get().load(uri.toString()).into(textImage)
            }
            textCardText.text = article.cardText
            textTitle.text = article.title

            carder.setOnClickListener{
                listener.onClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.article,parent,false)
        return  ArticleHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bind(ArticleList[position], listener, context)
    }

    override fun getItemCount(): Int {
        return ArticleList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun createElement(article: Article){
        ArticleList.add(article)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun createAll(articleList: MutableList<Article>){
        deleter()
        val articleList2 = mutableListOf<Article>()
        articleList.forEach {
            articleList2.add(it)
        }
        println(articleList2)
        ArticleList = articleList2 as ArrayList<Article>
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleter(){
        ArticleList.removeAll(ArticleList.toSet())
    }

    interface Listener{
        fun onClick(partner: Article)
    }
}