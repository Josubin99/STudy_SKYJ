package com.example.skyj.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.skyj.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_apply.*

class ApplyActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private lateinit var name : String
    private lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply)

        auth= FirebaseAuth.getInstance()

        val docRef=db.collection("users").document(auth.currentUser?.uid.toString())
        docRef.get().addOnSuccessListener {documentSnapshot ->

            name=documentSnapshot.get("name") as String
            email=documentSnapshot.get("email") as String
        }

        apply_upload_button.setOnClickListener {

            val form= hashMapOf(
                "text" to apply_content.text.toString(),
                "name" to name,
                "email" to email
            )

            db.collection("interview_apply")
                .add(form)
                .addOnSuccessListener { Toast.makeText(this, "신청완료", Toast.LENGTH_LONG).show() }
                .addOnFailureListener { Toast.makeText(this,"신청실패", Toast.LENGTH_LONG).show() }

            intent= Intent(this,PostInfoActivity::class.java)
            startActivity(intent)
        }

    }

}