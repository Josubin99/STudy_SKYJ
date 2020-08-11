package com.example.skyj.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.skyj.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth= FirebaseAuth.getInstance()

        join_login_button.setOnClickListener {

            auth.createUserWithEmailAndPassword(join_email_area.text.toString(), join_password_area.text.toString())
                .addOnCompleteListener(this){task ->

                    if(task.isSuccessful){

                        val intent=Intent(this, JoinInfoActivity::class.java)
                        startActivity(intent)

                    } else {

                        Toast.makeText(this,"회원가입에 실패하였습니다.",Toast.LENGTH_LONG).show()

                    }
                }
        }
    }
}