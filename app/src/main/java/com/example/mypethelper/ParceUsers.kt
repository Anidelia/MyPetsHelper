import com.example.mypethelper.Users

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class ParceUsers {

    fun parsUser(dataSnapshot: DataSnapshot): Users {
        println("------------------------------------------------------")
        println(dataSnapshot.child("Place").children.count())
        dataSnapshot.child("Place").children.forEach {
            println(dataSnapshot)
        }
        val user = Users(
            mail = dataSnapshot.child("Mail").value.toString(),
            name = dataSnapshot.child("Name").value.toString(),
            surname = dataSnapshot.child("Surname").value.toString(),
            ava = dataSnapshot.child("Avatarka").value.toString()
        )
        return user
    }

}