package com.example.skyj.write

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.skyj.R
import com.example.skyj.StudyActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.activity_write.post_content
import kotlinx.android.synthetic.main.activity_write.post_img
import kotlinx.android.synthetic.main.activity_write.post_img_upload_button
import kotlinx.android.synthetic.main.activity_write.post_title
import kotlinx.android.synthetic.main.activity_write.post_upload_button
import kotlinx.android.synthetic.main.activity_write4.*
import java.text.SimpleDateFormat
import java.util.*

class Write4Activity : AppCompatActivity() {

    var PICK_IMAGE_FROM_ALBUM=0
    var storage: FirebaseStorage?=null
    private lateinit var auth : FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    var photoUri: Uri?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write4)

        storage=FirebaseStorage.getInstance()

        //Initiate storage
        var photoPickerIntent= Intent(Intent.ACTION_PICK)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            Toast.makeText(this,"PERMISSION DENINED", Toast.LENGTH_LONG).show()
            finish()
        }


        post_img_upload_button.setOnClickListener {

            //Open the Album
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
        }


        auth=FirebaseAuth.getInstance()


        post_upload_button.setOnClickListener {
            contentUpload()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE_FROM_ALBUM){
            if(resultCode== Activity.RESULT_OK){
                //This is pat to the slected image
                photoUri=data?.data
                post_img.setImageURI(photoUri)

            }else{
                //Exit the addPhotoActivity if you leave the album without selecting it
                Toast.makeText(this,"사진을 선택해 주세요", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    fun contentUpload(){
        //Make filename
        var timeStamp= SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName="IMAGE_"+ timeStamp + "_.png"
        var storageRef=storage?.reference?.child("post_images")?.child(imageFileName)

        storageRef?.putFile(photoUri!!)?.addOnSuccessListener {
            val contentDTO=ContentDTO()
            val contentWriterDTO=ContentWriterDTO()
            val docRef=db.collection("users").document(auth.currentUser?.uid.toString())
            storageRef.downloadUrl
                .addOnSuccessListener {uri ->
                    contentDTO.imageUrl=uri.toString()
                    contentDTO.text=post_content.text.toString()
                    contentDTO.title=post_title.text.toString()
                    contentDTO.timestamp=System.currentTimeMillis()

                    db.collection("toiec_postInfo")?.document()?.set(contentDTO)
                        .addOnSuccessListener {
                            Toast.makeText(this,"업로드 성공", Toast.LENGTH_LONG).show()
                            val intent= Intent(this, StudyActivity::class.java)
                            startActivity(intent)

                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this,"업로드 실패", Toast.LENGTH_LONG).show()
                        }

                    docRef.get()
                        .addOnSuccessListener { documentSnapshot ->
                            contentWriterDTO.writer=documentSnapshot.get("name").toString()
                            contentWriterDTO.email=documentSnapshot.get("email").toString()
                            contentWriterDTO.major=documentSnapshot.get("major").toString()
                            contentWriterDTO.timestamp=contentDTO.timestamp

                            db.collection("toiec_post_writerInfo")?.document()?.set(contentWriterDTO)
                        }
                }
        }

    }
}