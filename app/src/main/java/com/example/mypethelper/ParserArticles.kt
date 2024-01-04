package com.example.mypethelper

import com.google.firebase.database.DataSnapshot


class ParserArticles {

    fun parsPalce(dataSnapshot: DataSnapshot): Article {
        val article = Article(
            cardText = dataSnapshot.child("cardText").value.toString(),
            title = dataSnapshot.child("title").value.toString(),
            image = dataSnapshot.child("image").value.toString(),
            content = dataSnapshot.child("content").value.toString(),
            id = dataSnapshot.key.toString().toInt()
        )
        return article
    }

    fun parsPalces(dataSnapshot: MutableList<DataSnapshot>): MutableList<Article> {
        var articles = mutableListOf<Article>()
        dataSnapshot.forEach { it ->

            val article = Article(
                cardText = it.child("cardText").value.toString(),
                title = it.child("title").value.toString(),
                image = it.child("image").value.toString(),
                content = it.child("content").value.toString(),
                id = it.key.toString().toInt()
            )
            articles.add(article)
        }
        return articles
    }
}