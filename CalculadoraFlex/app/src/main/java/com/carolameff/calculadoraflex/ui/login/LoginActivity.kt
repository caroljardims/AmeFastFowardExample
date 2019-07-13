package com.carolameff.calculadoraflex.ui.login

import android.app.Activity
import android.app.Activity.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carolameff.calculadoraflex.R
import com.carolameff.calculadoraflex.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class LoginActivity : AppCompatActivity() {

    companion object {
        const val RESULT = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        newAccountButton.setOnClickListener {
            val signup = Intent(this, SignUpActivity::class.java)
            startActivityForResult(signup, RESULT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT && resultCode == Activity.RESULT_OK) {
            inputEmail.setText(data?.getStringExtra("email"))
        }
    }

}
