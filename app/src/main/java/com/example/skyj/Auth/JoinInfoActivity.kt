package com.example.skyj.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.skyj.MainActivity
import com.example.skyj.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_join_info.*

class JoinInfoActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    private val db=FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_info)

        auth=FirebaseAuth.getInstance()

        join_info_login_button.setOnClickListener {
            val user= hashMapOf(
                "name" to join_info_name_area.text.toString(),
                "major" to join_info_major_area.text.toString(),
                "email" to join_info_email_area.text.toString()
            )

            db.collection("users")
                .document(auth.currentUser?.uid.toString())
                .set(user)
                .addOnSuccessListener { Log.e("JoinInfoActivity", "회원가입 완료")

                    val intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { Log.e("JoinInfoActivity", "회원가입에 실패하였습니다")

                    val intent=Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
        }
    }
}