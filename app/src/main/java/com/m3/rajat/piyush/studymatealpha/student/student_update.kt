package com.m3.rajat.piyush.studymatealpha.student
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.AdminModel
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityStudentUpdateBinding
import com.m3.rajat.piyush.studymatealpha.faculty.FacultyAdapter

class student_update : AppCompatActivity() {
    private lateinit var upd_name : EditText
    private lateinit var upd_email : EditText
    private lateinit var upd_password : EditText
    private lateinit var upd_class : EditText
    private lateinit var upd_image : ImageView
    private lateinit var upd_id:EditText
    private lateinit var btn_upd : Button
    private lateinit var btn_delete : Button

    private lateinit var binding : ActivityStudentUpdateBinding

    private lateinit var sqLiteHelper: SQLiteHelper
    private var adapter : FacultyAdapter?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        sqLiteHelper = SQLiteHelper(this)

        //Getting Data From View For Update
        upd_id.setText(intent.getIntExtra("student_id",0).toString())
        upd_name.setText(intent.getStringExtra("student_name"))
        upd_email.setText(intent.getStringExtra("student_email"))
        upd_password.setText(intent.getStringExtra("student_pass"))
        upd_class.setText(intent.getStringExtra("student_class"))
        if (intent.getByteArrayExtra("student_image")!=null){
            upd_image.setImageBitmap(BitmapFactory.decodeByteArray(intent.getByteArrayExtra("student_image"),0,intent.getByteArrayExtra("student_image")!!.size))
        }

        btn_upd.setOnClickListener {
            if(validation()){
                updateStudent()
            } else{
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
        }

        btn_delete.setOnClickListener {
            val email = intent.getStringExtra("student_email")
            if(email!=null) {
                deleteStudent(email)
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, student_view::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback {  }
    }

    private fun updateStudent() {
        val student = AdminModel( student_id = upd_id.text.toString().toInt(),student_name = upd_name.text.toString() , student_email = upd_email.text.toString(), student_class = upd_class.text.toString(), student_password = upd_password.text.toString())
        val rc =  sqLiteHelper.updateStudentById(student)
        if(rc > 0){
            getStudent()
            Toast.makeText(applicationContext,"Updated",Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, student_view::class.java))
        }else{
            Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStudent() {
        val admList = sqLiteHelper.getAllStudent()
        adapter?.addItems(admList)
    }

    private fun validation(): Boolean {
        if(upd_name.length() == 0){
            upd_name.error = "Name Required"
            return false
        } else if(upd_password.length()==0){
            upd_password.error = "Password Not Be Null"
            return false
        } else if(upd_class.length()==0){
            upd_class.error = "Class Can't Be Empty"
            return false
        }
        return true
    }

    private fun deleteStudent(studentEmail: String) {
        val builder =MaterialAlertDialogBuilder(this)
        builder.setTitle("Student")
        builder.setMessage("Do You Want To Delete This Student ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog,_->
            sqLiteHelper.DeleteStudent(studentEmail)
            getStudent()
            dialog.dismiss()
            startActivity(Intent(applicationContext, student_view::class.java))
        }
        builder.setNegativeButton("No"){ dialog,_->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }


    private fun initView() {
        upd_name = findViewById(R.id.Admin_update_student_name)
        upd_email = findViewById(R.id.Admin_update_student_email)
        upd_password = findViewById(R.id.Admin_update_student_pass)
        upd_class = findViewById(R.id.Admin_update_student_class)
        upd_image = findViewById(R.id.Admin_update_student_image)
        upd_id = findViewById(R.id.Admin_update_student_Id)
        btn_upd = findViewById(R.id.btnAdmin_update_student)
        btn_delete = findViewById(R.id.btnDeleteStudent)
    }
}