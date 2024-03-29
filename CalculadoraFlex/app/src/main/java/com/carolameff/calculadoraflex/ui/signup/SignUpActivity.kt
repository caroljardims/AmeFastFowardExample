package com.carolameff.calculadoraflex.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.carolameff.calculadoraflex.R
import com.carolameff.calculadoraflex.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()

        btCreate.setOnClickListener {
            mAuth.createUserWithEmailAndPassword(inputEmail.text.toString(), inputPassword.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        saveInRealTimeDatabase()
                    else
                        Toast.makeText(
                            this@SignUpActivity, it.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                }
        }
    }

    private fun saveInRealTimeDatabase() {
        val user = User(
            name = inputName.text.toString(),
            email = inputEmail.text.toString(),
            phone = inputPhone.text.toString()
        )
        FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        this, "Usuário criado com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                    val returnIntent = Intent()
                    returnIntent.putExtra("email", inputEmail.text.toString())
                    setResult(RESULT_OK, returnIntent)
                    finish()
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao criar o usuário", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

