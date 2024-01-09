package com.example.mypethelper.Parsers

import com.example.mypethelper.DataClasses.Users

import com.google.firebase.database.DataSnapshot

class ParceUsers {

    fun parsUser(dataSnapshot: DataSnapshot): Users {
        println("------------------------------------------------------")
        println(dataSnapshot.child("Article").children.count())
        dataSnapshot.child("Article").children.forEach {
            println(dataSnapshot)
        }
        val user = Users(
            mail = dataSnapshot.child("Mail").value.toString(),
            name = dataSnapshot.child("Name").value.toString(),
            surname = dataSnapshot.child("Surname").value.toString(),
            ava = dataSnapshot.child("Photo").child("Avatarka").value.toString(),
            fon = dataSnapshot.child("Photo").child("Fon").value.toString()
        )
        return user
    }

}