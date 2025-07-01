package com.m3.rajat.piyush.studymatealpha.faculty
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.admin.Admin_panel
import com.m3.rajat.piyush.studymatealpha.database.AdminModel
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityFacultyAddBinding
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class faculty_add : AppCompatActivity() {

    private lateinit var faculty_name : EditText
    private lateinit var faculty_email : EditText
    private lateinit var faculty_password : EditText
    private lateinit var faculty_sub : EditText

    private lateinit var faculty_image : ImageView
    private var byteArray: ByteArray? = null

    private lateinit var btn_add_faculty : Button
    private lateinit var btn_back : Button

    private val FAC_ID : Int = (2100000..2200000).random()
    private lateinit var binding : ActivityFacultyAddBinding
    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        sqLiteHelper = SQLiteHelper(this)
        btn_back.setOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
        }

        btn_add_faculty.setOnClickListener {
            if(faculty_validation()) {
                addFaculty()
                startActivity(Intent(applicationContext, Admin_panel::class.java))
            }
        }

        faculty_image.setOnClickListener {
            val  i = Intent(Intent.ACTION_GET_CONTENT)
            i.setType("image/*")
            ImageUploading.launch(i)
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback {  }
    }

    private fun addFaculty() {

        val name = faculty_name.text.toString()
        val email = faculty_email.text.toString()
        val pass = faculty_password.text.toString()
        val sub = faculty_sub.text.toString()

        val faculty = AdminModel(
            faculty_id = FAC_ID,
            faculty_image = byteArray,
            faculty_name = name,
            faculty_email = email,
            faculty_password = pass,
            faculty_sub = sub
        )

        val addFacultyRecord = sqLiteHelper.InsertFaculty(faculty)

        if(addFacultyRecord > -1){
            Log.d("icmain",addFacultyRecord.toString())
            Log.d("icmain",faculty.faculty_id.toString())
            Log.d("icmain",faculty.faculty_image.toString())

            Toast.makeText(applicationContext,"Faculty Added Successfully",Toast.LENGTH_SHORT).show()
            clearFaculty()
            finish()
        }else{
            Log.d("icmain",addFacultyRecord.toString())
            Toast.makeText(applicationContext,"Faculty Exists",Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun clearFaculty() {
        faculty_name.setText("")
        faculty_email.setText("")
        faculty_password.setText("")
        faculty_sub.setText("")
        faculty_name.requestFocus()
        faculty_image.setImageDrawable(resources.getDrawable(R.drawable.add_img))
    }

    @SuppressLint("SuspiciousIndentation")
    private val ImageUploading = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        rc -> if(rc.resultCode == RESULT_OK){
            val uri = rc.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                if(inputStream!=null) {
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream)
                    byteArray = byteArrayOutputStream.toByteArray()
                    if(byteArray!=null) {
                        if (byteArray!!.size / 1024 < 2048) {
                            faculty_image.setImageBitmap(bitmap)
                            inputStream.close()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Please choose image below 2mb",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "Input Stream Null", Toast.LENGTH_SHORT).show()
                }
            }catch (e : Exception){
                    e.printStackTrace()
            }
        }
    }
    //don't panic if u can't see a validation
    private fun faculty_validation(): Boolean {
        if(faculty_name.length() == 0){
            faculty_name.error = "Name Required"
            return false
        } else if(faculty_email.length()==0) {
            faculty_email.error = "Email Can't Be Empty"
            return false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(faculty_email.text.toString()).matches()){
            Toast.makeText(this,"Email Format Wrong !",Toast.LENGTH_SHORT).show()
            return false
        } else if(faculty_password.length()==0){
            faculty_password.error = "Password Required"
            return false
        } else if(faculty_sub.length()==0) {
            faculty_sub.error = "Subject Needed"
            return false
        }
        return true
    }

    private fun initView() {
        faculty_name = findViewById(R.id.Admin_add_faculty_name)
        faculty_email = findViewById(R.id.Admin_add_faculty_email)
        faculty_password = findViewById(R.id.Admin_add_faculty_pass)
        faculty_sub = findViewById(R.id.Admin_add_faculty_sub)
        faculty_image = findViewById(R.id.faculty_image)
        btn_add_faculty = findViewById(R.id.btnAdmin_add_faculty)
        btn_back = findViewById(R.id.btnBack)
    }
}