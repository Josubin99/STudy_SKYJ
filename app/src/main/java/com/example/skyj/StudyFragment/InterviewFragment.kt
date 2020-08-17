package com.example.skyj.StudyFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.skyj.Auth.LoginActivity
import com.example.skyj.Post.PostInfoActivity
import com.example.skyj.R
import com.example.skyj.write.ContentDTO
import com.example.skyj.write.ContentWriterDTO
import com.example.skyj.write.PostListAdapter
import com.example.skyj.write.WriteActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_interview.view.*
import kotlinx.android.synthetic.main.postlist_item.view.*

class InterviewFragment : Fragment() {

    private val db= FirebaseFirestore.getInstance()
    private lateinit var auth:FirebaseAuth

    private val title_array = ArrayList<String>()
    private val writer_array = ArrayList<String>()
    private val email_array = ArrayList<String>()
    private val image_array = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        auth=FirebaseAuth.getInstance()

        val view: View= inflater.inflate(R.layout.fragment_interview, container, false)


        val postlist_adapter=PostListAdapter(requireContext(), writer_array, title_array, image_array, email_array)
        view.post_listview.adapter=postlist_adapter

        db.collection("interview_postInfo")
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { result->
                for(document in result) {
                    title_array.add(document.get("title") as String)
                    image_array.add(document.get("imageUrl") as String)
                }
            }

        db.collection("interview_post_writerInfo")
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { result->
                for(document in result) {
                    email_array.add(document.get("email") as String)
                    writer_array.add(document.get("writer") as String)
                }

                postlist_adapter.notifyDataSetChanged()
            }

        view.post_listview.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(requireContext(), PostInfoActivity::class.java)
            startActivity(intent)
        }


        view.write_button.setOnClickListener {

            if(auth.currentUser==null){

                Toast.makeText(requireContext(), "로그인 후 이용 가능합니다", Toast.LENGTH_LONG).show()
                val intent=Intent(requireContext(),LoginActivity::class.java)
                startActivity(intent)

            } else{
                    val intent = Intent(requireContext(), WriteActivity::class.java)
                    startActivity(intent)

            }
        }

        return view
    }

}