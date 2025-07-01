package com.m3.rajat.piyush.studymatealpha.student
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
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
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityStudentAddBinding
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class student_add : AppCompatActivity() {
    private lateinit var student_name : EditText
    private lateinit var student_email : EditText
    private lateinit var student_password : EditText
    private lateinit var student_class : EditText

    private lateinit var student_image : ImageView
    private var byteArray: ByteArray? = null

    private lateinit var btn_add_student : Button
    private lateinit var btnBack : Button
    private  lateinit var binding : ActivityStudentAddBinding
    private val STUD_ID : Int = (2200000..2300000).random()

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        sqLiteHelper = SQLiteHelper(this)

        btnBack.setOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
        }

        btn_add_student.setOnClickListener {
            if(studentValidation()){
                addStudent()
                startActivity(Intent(applicationContext, Admin_panel::class.java))
            }
        }

        student_image.setOnClickListener {
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

    private fun addStudent() {
        val name = student_name.text.toString()
        val email = student_email.text.toString()
        val pass = student_password.text.toString()
        val sub = student_class.text.toString().uppercase()

        val student = AdminModel(
            student_id = STUD_ID,
            student_image = byteArray,
            student_name = name,
            student_email = email,
            student_password = pass,
            student_class = sub
        )

        val studentAdding = sqLiteHelper.InsertStudent(student)

        if(studentAdding > -1){
            Toast.makeText(applicationContext,"Student added successfully",Toast.LENGTH_SHORT).show()
            clearFields()
            finish()
        }else{
            Toast.makeText(applicationContext,"Cannot be add student",Toast.LENGTH_SHORT).show()
        }
    }

    private val ImageUploading = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        rc ->
        if (rc.resultCode == RESULT_OK) {
            val uri = rc.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream)
                byteArray = byteArrayOutputStream.toByteArray()
                if(byteArray!=null) {
                    if (byteArray!!.size / 1024 < 2048) {
                        student_image.setImageBitmap(bitmap)
                        inputStream!!.close()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Please choose image below 2mb",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun studentValidation(): Boolean {
        if(student_name.length() == 0){
            student_name.error = "Name Required"
            return false
        } else if(student_email.length()==0){
            student_email.error = "Email Can't Be Empty"
            return false
        } else if(student_password.length()==0) {
            student_password.error = "Password Required"
            return false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(student_email.text.toString()).matches()){
            Toast.makeText(this,"Email Format Is Wrong !",Toast.LENGTH_SHORT).show()
            return false
        } else if(student_class.length()==0) {
            student_class.error = "Class Needed"
            return false
        }
        return true
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun clearFields() {
        student_name.setText("")
        student_email.setText("")
        student_password.setText("")
        student_class.setText("")
        student_name.requestFocus()
        student_image.setImageDrawable(resources.getDrawable(R.drawable.add_img))
    }

    private fun initView() {
        student_name = findViewById(R.id.Admin_add_Student_name)
        student_email = findViewById(R.id.Admin_add_student_email)
        student_password = findViewById(R.id.Admin_add_student_pass)
        student_class = findViewById(R.id.Admin_add_student_class)
        btn_add_student = findViewById(R.id.btnAdmin_add_student)
        student_image = findViewById(R.id.student_image)
        btnBack = findViewById(R.id.btnBack)
    }
}