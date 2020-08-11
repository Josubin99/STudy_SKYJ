package com.example.skyj.StudyFragment

import android.content.Intent
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
import com.example.skyj.R
import com.example.skyj.write.ContentDTO
import com.example.skyj.write.ContentWriterDTO
import com.example.skyj.write.WriteActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_interview.view.*
import kotlinx.android.synthetic.main.postlist_item.view.*

class InterviewFragment : Fragment() {

    private val db= FirebaseFirestore.getInstance()

    private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        auth=FirebaseAuth.getInstance()

        val view: View= inflater.inflate(R.layout.fragment_interview, container, false)

        view.post_recyclerview.adapter=PostRecyclerViewAdapter()
        view.post_recyclerview.layoutManager=LinearLayoutManager(activity)


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

   inner class PostRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        var contentDTOs : ArrayList<ContentDTO> = arrayListOf()
        var contentUidList : ArrayList<String> = arrayListOf()
        var contentWriterDTOs : ArrayList<ContentWriterDTO> = arrayListOf()

        init{
            db?.collection("interview_postInfo")?.orderBy("timestamp")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                contentDTOs.clear()
                contentUidList.clear()
                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(ContentDTO::class.java)
                    contentDTOs.add(item!!)
                    contentUidList.add(snapshot.id)
                }
            }

            db?.collection("interview_post_writerInfo")?.addSnapshotListener { querySnapshot, firebaeFirestoreException ->
                contentWriterDTOs.clear()
                for (snapshot in querySnapshot!!.documents){
                    var item = snapshot.toObject(ContentWriterDTO::class.java)
                    contentWriterDTOs.add(item!!)
                }
            }



        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view=LayoutInflater.from(parent.context).inflate(R.layout.postlist_item,parent,false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun getItemCount(): Int {
            return contentDTOs.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewholder=(holder as CustomViewHolder).itemView

            viewholder.post_title.text=contentDTOs!![position].title
            Glide.with(holder.itemView.context).load(contentDTOs!![position].imageUrl).into(viewholder.post_img)
            viewholder.post_writer.text=contentWriterDTOs!![position].writer
            viewholder.post_email.text=contentWriterDTOs!![position].email
            viewholder.post_numlike.text="Likes "+ contentDTOs!![position].favoriteCount
        }

    }

}