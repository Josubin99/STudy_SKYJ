package com.example.skyj.Post

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.skyj.Auth.LoginActivity
import com.example.skyj.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_apply.*
import kotlinx.android.synthetic.main.fragment_apply.view.*

class ApplyFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private val db=FirebaseFirestore.getInstance()
    private val text_array= ArrayList<String>()
    private val nickname_array= ArrayList<String>()
    private val email_array=ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth= FirebaseAuth.getInstance()

        val view:View=inflater.inflate(R.layout.fragment_apply,container,false)

        val apply_adapter=ApplyListAdapter(requireContext(), nickname_array, text_array, email_array)
        view.apply_listview.adapter=apply_adapter

        db.collection("interview_apply")
            .get()
            .addOnSuccessListener { result->
                for(document in result) {
                    nickname_array.add(document.get("name") as String)
                    text_array.add(document.get("text") as String)
                    email_array.add(document.get("email") as String)
                }

                apply_adapter.notifyDataSetChanged()
            }

        view.apply_button.setOnClickListener {

            if (auth.currentUser==null){
                Toast.makeText(requireContext(), "로그인 후 이용가능합니다.", Toast.LENGTH_LONG).show()
                val intent=Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent= Intent(requireContext(), ApplyActivity::class.java)
                startActivity(intent)
            }
        }
        return view
    }

}