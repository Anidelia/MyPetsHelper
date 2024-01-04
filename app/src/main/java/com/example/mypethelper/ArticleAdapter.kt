package com.example.mypethelper

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mypethelper.databinding.ArticleBinding


class ArticleAdapter(val listener: Listener, val context: Context): RecyclerView.Adapter<ArticleAdapter.ArticleHolder>() {
    private var PlaceList=ArrayList<Article>()

    class ArticleHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ArticleBinding.bind(item)
        @RequiresApi(Build.VERSION_CODES.Q)
        fun bind(article: Article, listener: Listener, context: Context) = with(binding){
            //val adapterPager = PlacePhotoAdapter()
            //adapterPager.addImage(article.image)
            //image.adapter = adapterPager
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
        holder.bind(PlaceList[position], listener, context)
    }

    override fun getItemCount(): Int {
        return PlaceList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun createElement(partner: Article){
        PlaceList.add(partner)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun createAll(partnerList: MutableList<Article>){
        deleter()
        val partnerList2 = mutableListOf<Article>()
        partnerList.forEach {
            partnerList2.add(it)
        }
        println(partnerList2)
        PlaceList = partnerList2 as ArrayList<Article>
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleter(){
        PlaceList.removeAll(PlaceList.toSet())
    }

    interface Listener{
        fun onClick(partner: Article)
    }
}