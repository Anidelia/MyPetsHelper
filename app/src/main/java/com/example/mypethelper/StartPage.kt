package com.example.mypethelper

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mypethelper.databinding.ActivityStartpageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class StartPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var bind: ActivityStartpageBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth
        val shaderPref = getSharedPreferences("SPDB", Context.MODE_PRIVATE)

        val log = shaderPref.getString("login", null).toString()
        val pas = shaderPref.getString("password", null).toString()

        auth.signInWithEmailAndPassword(log, pas)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // успешный вход, переход в main activity
                    Toast.makeText(baseContext, "Authentication Success.",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Welcome!",
                        Toast.LENGTH_SHORT).show()
                }
            }
        super.onCreate(savedInstanceState)
        bind = ActivityStartpageBinding.inflate(layoutInflater)


        setContentView(bind.root)
        bind.buttonLogIn.setOnClickListener {
            startActivity(Intent(this@StartPage, LogIn::class.java))
        }
        bind.buttonReg.setOnClickListener {
            startActivity(Intent(this@StartPage, RegAct::class.java))
        }
    }
}