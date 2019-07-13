package com.carolameff.calculadoraflex.ui.login

import android.app.Activity
import android.app.Activity.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.carolameff.calculadoraflex.R
import com.carolameff.calculadoraflex.ui.form.FormActivity
import com.carolameff.calculadoraflex.ui.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class LoginActivity : AppCompatActivity() {

    companion object {
        const val RESULT = 1
    }

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null)
            goHome()

        loginButton.setOnClickListener {
            mAuth.signInWithEmailAndPassword(inputEmailLogin.text.toString(),
                inputPasswordLogin.text.toString()).addOnCompleteListener {
                if (it.isSuccessful) goHome()
                else Toast.makeText(this@LoginActivity, it.exception?.message,
                        Toast.LENGTH_SHORT).show()
            }
        }

        newAccountButton.setOnClickListener {
            val signup = Intent(this, SignUpActivity::class.java)
            startActivityForResult(signup, RESULT)
        }
    }

    private fun goHome() {
        val intent = Intent(this, FormActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT && resultCode == Activity.RESULT_OK) {
            inputEmailLogin.setText(data?.getStringExtra("email"))
        }
    }

}
