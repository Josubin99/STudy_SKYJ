package com.example.skyj.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.skyj.MainActivity
import com.example.skyj.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth= FirebaseAuth.getInstance()

        login_button.setOnClickListener {

            auth.signInWithEmailAndPassword(email_area.text.toString(), password_area.text.toString())
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){

                        val intent= Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    }else{

                        Toast.makeText(this,"아이디 또는 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show()

                    }
                }
        }

        join_button.setOnClickListener {

            val intent=Intent(this, JoinActivity::class.java)
            startActivity(intent)

        }
    }
}